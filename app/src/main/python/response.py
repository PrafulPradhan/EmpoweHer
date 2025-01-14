def chatbot_response(user_input):
    """Generate chatbot response (dummy function)."""
    # Replace this with your chatbot logic
    if "hello" in user_input.lower():
        return "Hi there! How can I assist you today?"
    elif "bye" in user_input.lower():
        return "Goodbye! Have a great day!"
    else:
        return "I'm sorry, I didn't understand that."