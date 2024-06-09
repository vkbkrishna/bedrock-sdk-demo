import streamlit as st
import requests as req
from requests.auth import HTTPBasicAuth
import json
import pandas as pd

st.set_page_config(layout="wide")

def getCall(user_query):
     data = {
          "prompt": user_query
     }
     # Define the URL and your credentials
     url = 'http://localhost:8080/ai/data/query1'
     username = 'sherlock'
     password = 'Winner@2024'
     headers = {
         'Content-Type': 'application/json'  # or the specific content type the server expects
     }
     response = req.post(url, headers=headers, auth=HTTPBasicAuth(username, password),data=json.dumps(data))
     # Check the response
     if response.status_code == 200:
        print('Success!')
     else:
        print(f'Failed with status code: {response.status_code}')
        print(response.text)
     return json.loads(response.text);

# Custom CSS to reduce space above the title
st.markdown("""
<style>
    .block-container {
        padding-top: 5rem;
        padding-bottom: 5rem;
        padding-left: 5rem;
        padding-right: 5rem;
    }
</style>
""", unsafe_allow_html=True)

def main():
    st.title("Team Sherlock - GenAI in Fraud Detection")

    # LLM Interaction
    user_query = st.text_input("Enter your question:")
    col1, col2 = st.columns(2, gap="small")
    with col1:
        send = st.button("Send", help="Run query")
    with col2:
        clear = st.button("Clear", help="Clear")

    if (len(user_query)!=0 | send) & clear!=1:
        try:
            llm_response = getCall(user_query)
            #for response in llm_response:
            data = pd.DataFrame(llm_response)
            st.divider()
            st.write(data)
            st.divider()
            #st.line_chart(data)
        except Exception as e:
            print(e)
            st.error(f"Unable to run your query at this time: {e}")
    if clear:
        st.divider()

if __name__ == "__main__":
    main()
