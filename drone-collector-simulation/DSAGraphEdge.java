/*REFERENCES: 
 Obtained from Practical 06*/

import java.io.*;//for interface Serializable

public class DSAGraphEdge implements Serializable
{
	private DSAGraphVertex fromVertex;
	private DSAGraphVertex toVertex;
	private String label;//label/"key" for edge
	private Object value;
	private boolean directed;
	
	public DSAGraphEdge(DSAGraphVertex fromVertex,DSAGraphVertex toVertex,String label,Object value,boolean directed)
	{
		setFromVertex(fromVertex);
		setToVertex(toVertex);
		setLabel(label);
		setValue(value);
		setDirected(directed);
	} 
	
	//setter 
	public void setFromVertex(DSAGraphVertex fromVertex)
	{
        this.fromVertex = fromVertex;
	}
	
	public void setToVertex(DSAGraphVertex toVertex)
	{
        this.toVertex = toVertex;
	}
	
	public void setLabel(String label)
	{
        this.label = label;
	}
	
	public void setValue(Object value)
	{
        this.value = value;
	}
	
	public void setDirected(boolean directed)
	{
        this.directed = directed;
	}
	
	//getter
	public DSAGraphVertex getFromVertex()
	{
        return this.fromVertex;
	}
	
	public DSAGraphVertex getToVertex()
	{
        return this.toVertex;
	}
	
	public String getLabel()
	{
        return this.label;
	}
	
	public Object getValue()
	{
        return this.value;
	}
	
	//check is directed edge or not(TRUE if it is directed edge)
	public boolean isDirected()
	{
        return this.directed;
	}
	
	public String toString()
	{
        return "\nfromVertex: " + fromVertex + ". toVertex: " + toVertex + ". label: " + label + ". value: " + value + ". directed: " + directed;
	}
}
