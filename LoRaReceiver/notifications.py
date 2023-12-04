import smtplib
from email.mime.text import MIMEText
from email.mime.multipart import MIMEMultipart

import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

# Use the service account credentials JSON file to initialize the Firebase Admin SDK
# cred = credentials.Certificate('path/to/serviceAccountKey.json')
# firebase_admin.initialize_app(cred)

# Initialize Firestore database
# db = firestore.client()

desired_id = "12:12:12345"
users_ref = db.collection('DrainFlow_Users')
query = users_ref.where('id', '==', desired_id).order_by('private_device_id')

# Get documents based on the query
docs = query.get()

for doc in docs:
    # Check if the document has the field 'enableEmail'
    if 'enableEmail' in doc.to_dict():
        enable_email = doc.to_dict()['enableEmail']
        if enable_email:
            print("Get email")
        else: 
            pass 
    else:
        pass

# Set your Outlook credentials
outlook_email = 'drainflowsolutionsinc@outlook.com'
outlook_password = 'drainflow2023'

# Email details
sender_email = 'drainflowsolutionsinc@outlook.com'
receiver_email = 'philipcarlssoncoulombe@gmail.com'
subject = 'Subject: Test Email via Outlook'
body = 'This is a test email sent via Python using Outlook.'

# Create the email message
message = MIMEMultipart()
message['From'] = sender_email
message['To'] = receiver_email
message['Subject'] = subject
message.attach(MIMEText(body, 'plain'))

# Connect to Outlook's SMTP server
try:
    server = smtplib.SMTP('smtp-mail.outlook.com', 587)
    server.starttls()
    # Login to your Outlook account
    server.login(outlook_email, outlook_password)
    # Send email
    server.sendmail(sender_email, receiver_email, message.as_string())
    print("Email sent successfully!")
except Exception as e:
    print(f"Failed to send email. Error: {e}")

# Quit the server
server.quit()
