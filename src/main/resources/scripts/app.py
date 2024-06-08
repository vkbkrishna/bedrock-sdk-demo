import streamlit as st
import requests as req
import re
import json
import pandas as pd

st.set_page_config(layout="wide")

def getCall(user_query):
     data = {
          "prompt": user_query
     }
     response = req.post('http://localhost:8080/ai/data/query1', json=data)
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
