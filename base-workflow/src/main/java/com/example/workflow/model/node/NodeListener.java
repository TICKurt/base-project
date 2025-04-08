package com.example.workflow.model.node;

import lombok.Data;

@Data
public class NodeListener {
    private String event;
    private String implementation;
    private String implementationType;
}
