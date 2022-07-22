'''
message DataTracking {
  required string version = 1;
  required string name = 2;
  required fixed64 timestamp = 3;
  optional string phone_id = 4;
  optional fixed64 lon = 5;
  optional fixed64 lat = 6;
}

'''
import random
import pandas as pd

# gen message DataTracking
def gen_message_DataTracking(version: str, name: str, timestamp: int, phone_id: str, lon: int, lat: int):
    message = {
        'version': version,
        'name': name,
        'timestamp': timestamp,
        'phone_id': phone_id,
        'lon': lon,
        'lat': lat
    }
    return message

# random message DataTracking
def random_message_DataTracking():
    os = ['android', 'ios']
    version = ['1.0', '1.1', '1.2', '1.3', '1.4', '1.5', '1.6', '1.7', '1.8', '1.9', '2.0', '2.1', '2.2', '2.3', '2.4', '2.5', '2.6', '2.7', '2.8', '2.9', '3.0', '3.1', '3.2', '3.3', '3.4', '3.5', '3.6', '3.7', '3.8', '3.9', '4.0', '4.1', '4.2', '4.3', '4.4', '4.5', '4.6', '4.7', '4.8', '4.9', '5.0', '5.1', '5.2', '5.3', '5.4', '5.5', '5.6', '5.7', '5.8', '5.9', '6.0', '6.1', '6.2', '6.3', '6.4', '6.5', '6.6', '6.7', '6.8', '6.9', '7.0', '7.1', '7.2', '7.3', '7.4', '7.5', '7.6', '7.7', '7.8', '7.9', '8.0', '8.1', '8.2', '8.3', '8.4', '8.5', '8.6', '8.7', '8.8', '8.9', '9.0', '9.1', '9.2', '9.3', '9.4', '9.5', '9.6', '9.7', '9.8', '9.9', '10.0']
    
    #list of phone name
    phone_df = pd.read_csv('phone_data.csv')
    phone_name = phone_df['name'].tolist()

    #list of phone_id
    phone_id = phone_df['version'].tolist()

    #list of random unix timestamp

    timestamp = random.randint(1000000000, 9999999999)

    # random message DataTracking
    messages = []
    for i in range(0, 1000000):
        message = {
            'version': random.choice(os) + ' '+ random.choice(version),
            'name': random.choice(phone_name),
            'timestamp': random.randint(1000000000, 4000000000),
            'phone_id': random.choice(phone_id),
            'lon': random.randint(0, 180),
            'lat': random.randint(0, 90)
        }
        messages.append(message)

    #export to csv
    df = pd.DataFrame(messages)
    df.to_csv('messages.csv', index=False)





if __name__ == '__main__':
    # df = pd.read_csv('archive/phones_data.csv')


    # # remove rows without '(' character
    # df = df[df['model_name'].str.contains('\(')]

    # # split model_name column into two columns - name and version
    # df['name'] = df['model_name'].str.split('\(').str[0].str.strip()
    # df['version'] = df['model_name'].str.split('\(').str[1].str[:-1].str.strip()

    # # remove rows without name and version
    # df = df[df['name'].notnull()]
    # df = df[df['version'].notnull()]

    # #just use name and version columns
    # df = df[['name', 'version']]

    # # remove duplicates from version column
    # df = df.drop_duplicates(subset=['version'])

    # #export to csv
    # df.to_csv('phone_data.csv', index=False)
    # print(df)
    random_message_DataTracking()

