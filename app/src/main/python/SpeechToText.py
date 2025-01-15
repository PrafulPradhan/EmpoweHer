import speech_recognition as sr
def speech_to_text():

    # Use 'Recognizer()' as before
    recognizer = sr.Recognizer()

    # Use the microphone for input with 'SoundDevice'
    with sr.Microphone(device_index=None) as source:
        # print("Please speak into the microphone...")
        recognizer.adjust_for_ambient_noise(source)
        audio = recognizer.listen(source)

        try:
            text = recognizer.recognize_google(audio)
            # print("You said:", text)
            return text
        except sr.UnknownValueError:
            # print("Sorry, I could not understand the audio.")
            return "Sorry, I could not understand the audio."
        except sr.RequestError as e:
            # print(f"Could not request results; {e}")
            return "Could not request results"

# import sounddevice as sd
# import numpy as np
# import speech_recognition as sr
#
# def speech_to_text():
#     # Configuration
#     duration = 5  # Recording duration in seconds
#     samplerate = 44100  # Sampling rate
#     channels = 1  # Mono audio
#
#     # print("Please speak into the microphone...")
#
#     # Record audio using sounddevice
#     audio_data = sd.rec(int(duration * samplerate), samplerate=samplerate, channels=channels, dtype=np.int16)
#     sd.wait()  # Wait until the recording is finished
#     # print("Recording complete. Processing...")
#
#     # Convert audio data to a format compatible with speech_recognition
#     audio_bytes = audio_data.tobytes()
#
#     # Create an AudioData object
#     recognizer = sr.Recognizer()
#     audio = sr.AudioData(audio_bytes, samplerate, 2)  # 2 bytes per sample (int16)
#
#     try:
#         # Use Google Web Speech API to recognize text
#         text = recognizer.recognize_google(audio)
#         # print("You said:", text)
#         return text
#     except sr.UnknownValueError:
#         # print("Sorry, I could not understand the audio.")
#         return "Sorry, I could not understand the audio."
#     except sr.RequestError as e:
#         # print(f"Could not request results; {e}")
#         return "Could not request results"
