package com.example.benchmark

import android.health.connect.datatypes.units.Power
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.PowerCategory
import androidx.benchmark.macro.PowerMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.content.Intent
import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.benchmark.macro.TraceSectionMetric
import androidx.test.filters.SdkSuppress
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until

import junit.framework.TestCase
import perfetto.protos.AndroidCpuMetric

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @OptIn(ExperimentalMetricApi::class)
    @RequiresApi(Build.VERSION_CODES.Q)
    @Test
    fun startup() = benchmarkRule.measureRepeated(
        packageName = "com.example.empoweher",
        metrics = listOf(PowerMetric(type= PowerMetric.Battery())),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
    }

    @Test
    fun scrollAndNavigate() = benchmarkRule.measureRepeated(
        packageName = "com.example.empoweher",
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()

        addElementsAndScrollDown()
        device.waitForIdle()
        Thread.sleep(1000)
    }
}

fun MacrobenchmarkScope.addElementsAndScrollDown() {
    val button = device.findObject(By.text("Click me"))
    val list = device.findObject(By.res("item_list"))

    repeat(30) {
        button.click()
    }

    device.waitForIdle()

    list.setGestureMargin(device.displayWidth / 5)
    list.fling(Direction.DOWN)

    device.findObject(By.text("Element 29")).click()
    device.waitForIdle()
    device.wait(Until.hasObject(By.text("Detail: Element 29")), 5000)
}

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalMetricApi::class)
class ScrollBenchmark {

    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    @SdkSuppress(minSdkVersion = 24)
    fun noCompilation() = scroll(CompilationMode.None())

    @Test
    fun defaultCompilation() = scroll(CompilationMode.DEFAULT)

    @Test
    fun full() = scroll(CompilationMode.Full())

    private fun scroll(compilationMode: CompilationMode) {
        var firstStart = true
        benchmarkRule.measureRepeated(
            packageName = "com.example.empoweher",
            metrics = listOf(
                TraceSectionMetric("ClickTrace"),
                FrameTimingMetric()
            ),
            compilationMode = compilationMode,
            startupMode = StartupMode.COLD,
            iterations = 5,
            setupBlock = {
                if (firstStart) {
                    val intent = Intent()
                    intent.setClassName("com.example.empoweher", "com.example.empoweher.activities.ScrollActivity")
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivityAndWait(intent)
                    firstStart = false
                }
            }
        ) {
            device.wait(Until.hasObject(By.scrollable(true)), 5_000)

            val scrollableObject = device.findObject(By.scrollable(true))
            if (scrollableObject == null) {
                TestCase.fail("No scrollable view found in hierarchy")
            }
            scrollableObject.setGestureMargin(device.displayWidth / 10)
            scrollableObject?.apply {
                repeat(2) {
                    fling(Direction.DOWN)
                }
                repeat(2) {
                    fling(Direction.UP)
                }
            }
        }
    }
}