def text_to_speech(sentence, counter):
    import asyncio
    import edge_tts
    import nest_asyncio

    # Apply patch for Jupyter Notebook
    nest_asyncio.apply()

    # List of available voices
    VOICES = ['hi-IN-MadhurNeural','hi-IN-SwaraNeural']

    # Prompt user for text and voice
    TEXT = sentence

    # voice_index = int(input("Select a voice (1-2): ")) - 1
    VOICE = VOICES[1]

    # Output file
    OUTPUT_FILE = f"audio{counter}.mp3"

    async def amain() -> None:
        communicate = edge_tts.Communicate(TEXT, VOICE)
        await communicate.save(OUTPUT_FILE)

    # Run the asynchronous function
    asyncio.run(amain())
    # print(f"Audio saved to {OUTPUT_FILE}")