package jeecg.workflow.entity.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.core.annotation.JeecgEntityTitle;
import org.jeecgframework.workflow.pojo.base.TSBaseBus;

/**   
 * @Title: Entity
 * @Description: 入职申请表单
 * @author zhangdaihao
 * @date 2013-03-19 17:27:08
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_zhang", schema = "")
@SuppressWarnings("serial")
@JeecgEntityTitle(name="入职面试流程")
public class TBZhangEntity extends TSBaseBus implements java.io.Serializable {
	/**名字*/
	private java.lang.String title;
	/**年龄*/
	private java.lang.Integer age;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名字
	 */
	@Column(name ="TITLE",nullable=false)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名字
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  年龄
	 */
	@Column(name ="AGE",nullable=false)
	public java.lang.Integer getAge(){
		return this.age;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  年龄
	 */
	public void setAge(java.lang.Integer age){
		this.age = age;
	}
}
