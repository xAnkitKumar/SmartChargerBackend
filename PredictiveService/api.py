from flask import Flask, request, jsonify
import joblib
import pandas as pd

app = Flask(__name__)

# Load trained model
model = joblib.load("/Users/prathamgupta/Desktop/SmartCharger/BackEnd/PredictiveService/xgb_wait_time_model.joblib")


@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Get JSON data from request
        data = request.get_json()

        # Convert to DataFrame (handles both single and multiple records)
        if isinstance(data, dict):
            input_df = pd.DataFrame([data])
        else:
            input_df = pd.DataFrame(data)

        # Predict wait time
        predictions = model.predict(input_df)

        # Return predictions as JSON
        return jsonify({"predictions": predictions.tolist()})
    
    except Exception as e:
        return jsonify({"error": str(e)}), 400

# Run app
if __name__ == '__main__':
    app.run(debug=True)
