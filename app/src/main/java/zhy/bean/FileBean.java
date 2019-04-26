package zhy.bean;

import zhy.tree.bean.TreeNodeId;
import zhy.tree.bean.TreeNodeLabel;
import zhy.tree.bean.TreeNodePid;

public class FileBean
{
	@TreeNodeId
	private int _id;
	@TreeNodePid
	private int parentId;
	@TreeNodeLabel
	private String name;
	private long length;
	private String desc;

	public FileBean(int _id, int parentId, String name)
	{
		super();
		this._id = _id;
		this.parentId = parentId;
		this.name = name;
	}

	public int get_id() {
		return _id;
	}

	public String toString(){
		return "id-"+_id+"-parentId-"+parentId+"-name-"+name;
	}

}
