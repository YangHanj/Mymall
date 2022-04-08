package iee.yh.Mymall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import iee.yh.common.validator.group.AddGroup;
import iee.yh.common.validator.group.TrueStatues;
import iee.yh.common.validator.group.UpdateGroup;
import lombok.Data;
import org.apache.ibatis.annotations.Param;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * 品牌
 * 
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-03 08:57:06
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@NotNull(message = "修改必须指定id",groups = UpdateGroup.class)
	@Null(message = "新增不能指定id",groups = AddGroup.class)
	@TableId
	private Long brandId;
	/**
	 *
	 */
	@NotBlank(message = "品牌名称不能为空",groups = {UpdateGroup.class,AddGroup.class})
	private String name;
	/**
	 * 品牌logo地址
	 */
	@NotEmpty(message = "logo不能为空",groups = {AddGroup.class})
	@URL(message = "logo必须是一个合法的地址",groups = {UpdateGroup.class,AddGroup.class})
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@TrueStatues(vals = {0,1},message = "状态码不合法",groups = AddGroup.class)
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@Pattern(regexp = "^[a-zA-Z]$",message = "检索字母不合法")
	private String firstLetter;
	/**
	 * 排序
	 */
	@Min(value = 0,message = "排序必须是一个正整数")
	private Integer sort;

}
