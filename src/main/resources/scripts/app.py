import streamlit as st
import requests as req
import re
import json
import pandas as pd

st.set_page_config(layout="wide")

def getUserQueryResult(user_query):
     data_obj = {
          "prompt": user_query
     }
     url = "http://localhost:8080/ai/data/query1"
     return postCall(data_obj, url)

def postCall(req_obj, url):
     response = req.post(url, json=req_obj)
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
    
    col1, col2, col3 = st.columns([17,1,1])
    with col1:
        user_query = st.text_input("Enter your question:")
    with col2:
        send = st.button("Send", help="Run query")
    with col3:
        clear = st.button("Clear", help="Clear")
    print(clear)
    if (len(user_query)!=0 | send) & clear!=True:
        try:
            llm_response = getUserQueryResult(user_query)
            #for response in llm_response:
            data_frame = pd.DataFrame(llm_response)
            st.divider()
            st.write(data_frame)
            st.line_chart(data_frame,x="col1",y="col2")
            st.divider()
            #st.line_chart(data)
        except Exception as e:
            print(e)
            st.error(f"Unable to run your query at this time: {e}")
    if clear:
        st.divider()

if __name__ == "__main__":
    main()
