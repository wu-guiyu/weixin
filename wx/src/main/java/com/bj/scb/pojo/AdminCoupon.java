package com.bj.scb.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 管理员维护的优惠劵表
 *
 * @author sujiangming
 */
@Entity
@Table(name = "t_admin_coupon")
public class AdminCoupon {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column
    private String id;// 主键
    @Column
    private String creator_id;// 创建人ID
    @Column
    private String creator_name;// 创建人名称
    @Column
    private String cp_name;//优惠劵名称
    @Column
    private Integer cp_amount;//优惠劵金额
    @Column
    private Timestamp cp_start_time;// 开始时间
    @Column
    private Timestamp cp_end_time;// 结束时间
    @Column
    private int cp_is_publish;//优惠券的状态：0 为未发布，1 为已发布，2为已撤销
    @Column
    private String cp_publish_status;//优惠券发布状态描述：未发布，已发布，已撤销
    @Column
    private Timestamp cp_create_time;// 创建时间
    @Column
    private Timestamp cp_update_time;// 更新时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }

    public String getCreator_name() {
        return creator_name;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
    }

    public String getCp_name() {
        return cp_name;
    }

    public void setCp_name(String cp_name) {
        this.cp_name = cp_name;
    }

    public Integer getCp_amount() {
        return cp_amount;
    }

    public void setCp_amount(Integer cp_amount) {
        this.cp_amount = cp_amount;
    }

    public Timestamp getCp_start_time() {
        return cp_start_time;
    }

    public void setCp_start_time(Timestamp cp_start_time) {
        this.cp_start_time = cp_start_time;
    }

    public Timestamp getCp_end_time() {
        return cp_end_time;
    }

    public void setCp_end_time(Timestamp cp_end_time) {
        this.cp_end_time = cp_end_time;
    }

    public int getCp_is_publish() {
        return cp_is_publish;
    }

    public void setCp_is_publish(int cp_is_publish) {
        this.cp_is_publish = cp_is_publish;
    }

    public String getCp_publish_status() {
        return cp_publish_status;
    }

    public void setCp_publish_status(String cp_publish_status) {
        this.cp_publish_status = cp_publish_status;
    }

    public Timestamp getCp_create_time() {
        return cp_create_time;
    }

    public void setCp_create_time(Timestamp cp_create_time) {
        this.cp_create_time = cp_create_time;
    }

    public Timestamp getCp_update_time() {
        return cp_update_time;
    }

    public void setCp_update_time(Timestamp cp_update_time) {
        this.cp_update_time = cp_update_time;
    }
}
