import firebase_admin
from firebase_admin import credentials, firestore

# Initialize Firebase
cred = credentials.Certificate('/home/pi/Desktop/DrainFlow Solutions/project390-70e71-firebase-adminsdk-ksnfo-f3cdec1263.json')
firebase_admin.initialize_app(cred)

# Reference to your Firestore database
db = firestore.client()

# Collection to store data
collection_name = 'RPIdata'

# Check if there are existing documents
existing_documents = db.collection(collection_name).get()

if len(existing_documents) == 0:
    document_id = "1"  # If there are no existing documents, start with ID "1"
else:
    # Find the highest existing ID and increment by one
    existing_ids = [int(doc.id) for doc in existing_documents]
    next_id = str(max(existing_ids) + 1)
    document_id = next_id

# Reference to the chosen document
document_ref = db.collection(collection_name).document(document_id)

# Data to be sent
data = {
    'time': firestore.SERVER_TIMESTAMP,
    'rate': 10,
    'state': "Medium"
}

# Set data in the document
document_ref.set(data)

print(f"Data sent to document {document_id} in collection {collection_name} in Firestore")
