import requests
def fetchAPIData(QRCode, username, password):
    url = "http://www.sail-steel.com/qrcode/QRCode_FetchSet?sap-client=600"

    headers = {
        "Accept": "application/json",
        "X-Requested-With": "X"
    }

    # Data to send in the request body
    data = {
        "Opid": "OP14",
        "QRCode": QRCode,
        "HdrToItem": []
    }
    response = requests.post(url, auth=(username, password), headers=headers, json=data)
    if response.status_code == 200:
        return response.text
    return ""
