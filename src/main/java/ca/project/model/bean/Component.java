package ca.project.model.bean;

import lombok.Data;

@Data
public class Component {
    private String componentCode;

    public boolean equals(Component other){
        return componentCode.equalsIgnoreCase(other.getComponentCode());
    }
}
