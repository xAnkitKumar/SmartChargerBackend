import pandas as pd
import xgboost as xgb
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import OneHotEncoder
from sklearn.metrics import mean_squared_error
from sklearn.compose import ColumnTransformer
from sklearn.pipeline import Pipeline
import joblib

# Load the dataset
df = pd.read_csv("/Users/prathamgupta/Desktop/SmartCharger/BackEnd/PredictiveService/EV_ZeroAvailablePorts_5000.csv")

# Feature engineering
df['datetime'] = pd.to_datetime(df['datetime'])
df['hour'] = df['datetime'].dt.hour
df['day_of_week'] = df['datetime'].dt.dayofweek
df['is_weekend'] = df['day_of_week'].isin([5, 6]).astype(int)

# Features and target
features = [
    'station_id', 'total_ports', 'available_ports', 'charging_rate_kw',
    'hour', 'day_of_week', 'is_weekend', 'city', 'charger_type'
]
target = 'wait_time_min'

X = df[features]
y = df[target]

# One-hot encoding for categorical columns
categorical_features = ['city', 'charger_type']
numerical_features = list(set(features) - set(categorical_features))

# Define preprocessing and model pipeline
preprocessor = ColumnTransformer([
    ('onehot', OneHotEncoder(handle_unknown='ignore'), categorical_features)
], remainder='passthrough')

pipeline = Pipeline([
    ('preprocessor', preprocessor),
    ('regressor', xgb.XGBRegressor(objective='reg:squarederror', n_estimators=100, random_state=42))
])

# Train-test split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

# Fit the model
pipeline.fit(X_train, y_train)

# Evaluate (optional)
y_pred = pipeline.predict(X_test)
rmse = mean_squared_error(y_test, y_pred)
print(f"RMSE: {rmse:.2f}")

# Save the model
joblib.dump(pipeline, "/Users/prathamgupta/Desktop/SmartCharger/BackEnd/PredictiveService/xgb_wait_time_model.joblib")