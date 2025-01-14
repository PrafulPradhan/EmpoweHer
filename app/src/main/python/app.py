from SpeechToText import speech_to_text
from TextToSpeech import text_to_speech
from response import chatbot_response
from playsound import playsound

def driver():
    counter = 1
    while True:
        user_text = speech_to_text()
        if user_text is None:
            text_to_speech("I couldn't hear you. Please try again.")
            continue

        response = chatbot_response(user_text)
        text_to_speech(response, counter)
        playsound(f'app/src/main/res/raw/audio{counter}.mp3')

        counter = counter + 1
        # print('playing sound using  playsound')
        if counter == 5:
            break

        if "bye" in user_text.lower():
            break