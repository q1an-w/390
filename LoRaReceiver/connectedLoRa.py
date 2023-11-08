from time import sleep
from datetime import datetime
from SX127x.LoRa import *
from SX127x.board_config import BOARD
import firebase_admin
from firebase_admin import credentials, firestore

BOARD.setup()

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

class LoRaRcvCont(LoRa):
    def __init__(self, verbose=False):
        super(LoRaRcvCont, self).__init__(verbose)
        self.set_mode(MODE.SLEEP)
        self.set_dio_mapping([0] * 6)

    def start(self):
        self.reset_ptr_rx()
        self.set_mode(MODE.RXCONT)
        while True:
            sleep(2)
            rssi_value = self.get_rssi_value()
            status = self.get_modem_status()
            sys.stdout.flush()

    def on_rx_done(self):
        self.clear_irq_flags(RxDone=1)

        payload = self.read_payload(nocheck=True)
        data_str = bytes(payload).decode("utf-8",'ignore')

        # Extract flow rate and state
        flow_rate, state = data_str.split(', ')
        
        if state == '0':
            state = 'No Flow'
        elif state == '1':
            state = 'Low'
        elif state == '2':
            state = 'Medium'
        else:
            state = 'High'

        # Convert flow rate to integer
        flow_rate = int(flow_rate)

        # Data to be sent to Firebase
        data = {
            'time': firestore.SERVER_TIMESTAMP,
            'rate': flow_rate,
            'state': state
        }

        global document_id  # Use the global variable

        # Increment document_id
        document_id = str(int(document_id) + 1)

        # Update document reference
        global document_ref
        document_ref = db.collection(collection_name).document(document_id)

        # Set data in the document
        document_ref.set(data)

        print(f"Data sent to document {document_id} in collection {collection_name} in Firestore")

        self.set_mode(MODE.SLEEP)
        self.reset_ptr_rx()
        self.set_mode(MODE.RXCONT)

lora = LoRaRcvCont(verbose=False)
lora.set_mode(MODE.STDBY)
lora.set_pa_config(pa_select=1)

try:
    lora.start()
except KeyboardInterrupt:
    sys.stdout.flush()
    print("")
    sys.stderr.write("KeyboardInterrupt\n")
finally:
    sys.stdout.flush()
    print("")
    lora.set_mode(MODE.SLEEP)
    BOARD.teardown()
