import pickle
import json
import numpy as np
import os

def predict_from_json(json_input):

    # Load the trained model
    filename = os.path.join(os.path.dirname(__file__), "logistic_model.pkl")

# Load the model
    with open(filename, "rb") as file:
        model = pickle.load(file)

    # Parse JSON string into a Python list
    input_data = json.loads(json_input)

    # Convert list of dicts into a NumPy array (excluding 'userId')
    feature_keys = [key for key in input_data[0] if key != "userId"]
    input_array = np.array([[entry[key] for key in feature_keys] for entry in input_data])

    # Make predictions
    predictions = model.predict(input_array)

    return predictions.tolist()

# Example usage
# if __name__ == "__main__":
#     json_input = '[[5.1, 3.5, 1.4, 0.2], [6.2, 3.4, 5.4, 2.3]]'
#     pickle_file = "model.pkl"
#
#     result = predict_from_json(pickle_file, json_input)
