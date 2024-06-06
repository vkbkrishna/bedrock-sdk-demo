package com.bk.bedrocksdkdemo.converstation;

import com.bk.bedrocksdkdemo.aimodels.Claude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataBaseConversation {

    @Autowired
    private Claude claude;

    private void connect(){

    }
}
