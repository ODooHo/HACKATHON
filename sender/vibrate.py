import requests
import random


a = random.randint(1,100)
if __name__ == "__main__":  
    url = 'http://203.253.128.177:7579/Mobius/sch_platform_4_/sensed_vibration'
    headers =    {
                    'Accept':'application/json',
                    'X-M2M-RI':'12345',
                    'X-M2M-Origin':'Ssch_platform_4_', # change to your aei
                    'Content-Type':'application/vnd.onem2m-res+json; ty=4'
                }
    data =    {
                "m2m:cin": {
                    "con": f"{a}"
                }
        }   

    r = requests.post(url, headers=headers, json=data)

    try:
        r.raise_for_status()
        print(r)
    except Exception as exc:
        print('There was a problem: %s' % (exc))

