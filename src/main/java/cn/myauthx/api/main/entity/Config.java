package cn.myauthx.api.main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author DaenMax
 * @since 2022-01-06
 */
@Data
@Accessors(chain = true)
@TableName("ma_config")
public class Config extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 钉钉机器人
     */
    private String dingbotAccessToken;

    /**
     * 0=关闭通知，1=开启通知
     */
    private String botMsg;

    /**
     * 添加软件扣除点数
     */
    private BigDecimal softPrice;

    /**
     * 0=维护，1=正常
     */
    private String webStatus;

    private String seoTitle;

    private String seoKeywords;

    private String seoDescription;


}