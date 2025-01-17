package cn.daenx.myauth.main.service;

import cn.daenx.myauth.base.vo.Result;
import cn.daenx.myauth.main.entity.Event;
import cn.daenx.myauth.base.vo.MyPage;
import cn.daenx.myauth.main.entity.Soft;
import cn.daenx.myauth.main.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author DaenMax
 * @since 2022-01-06
 */
public interface IEventService extends IService<Event> {
    /**
     * 触发事件
     *
     * @param name
     * @param user
     * @param soft
     * @return
     */
    Result letEvent(String name, User user, Soft soft, String token);

    /**
     * 获取事件列表
     *
     * @param event
     * @param myPage
     * @return
     */
    Result getEventList(Event event, MyPage myPage);

    /**
     * 查询事件
     *
     * @param event
     * @return
     */
    Result getEvent(Event event);

    /**
     * 修改事件
     *
     * @param event
     * @return
     */
    Result updEvent(Event event);

    /**
     * 添加事件
     *
     * @param event
     * @return
     */
    Result addEvent(Event event);

    /**
     * 删除事件
     *
     * @param event
     * @return
     */
    Result delEvent(Event event);
}
