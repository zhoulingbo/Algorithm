package pers.zhoulingbo.algorithm.ml;

import java.util.ArrayList;
import java.util.List;

public class Node
{
    private String name;

    List<String> labelList = new ArrayList<>();     // 节点与子节点的边(标签)
    List<Node> childList = new ArrayList<>();       // 子节点 

    public Node(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
}
