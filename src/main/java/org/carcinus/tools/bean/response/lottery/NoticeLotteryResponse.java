package org.carcinus.tools.bean.response.lottery;

import lombok.Data;
import org.carcinus.tools.bean.response.Response;


@Data
public class NoticeLotteryResponse extends Response {
    private NoticeLotteryData data;
}
