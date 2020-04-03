package com.bj.scb.pojo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 用户获取的优惠劵表
 *
 * @author sujiangming
 */
@Entity
@Table(name = "t_user_coupon")
public class UserCoupon {
    @Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    @Column
    private String id;// 主键
    @Column
    private String cp_owner;// 用户ID
    @Column
    private String cp_name;//优惠劵名称
    @Column
    private Integer cp_amount;//优惠劵金额
    @Column
    private Timestamp cp_start_time;// 开始时间
    @Column
    private Timestamp cp_end_time;// 结束时间
    @Column
    private int cp_status;//优惠券的状态：1 为可用状态，0 为不可用状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCp_owner() {
        return cp_owner;
    }

    public void setCp_owner(String cp_owner) {
        this.cp_owner = cp_owner;
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

    public int getCp_status() {
        return cp_status;
    }

    public void setCp_status(int cp_status) {
        this.cp_status = cp_status;
    }
}
