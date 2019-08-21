package life.majiang.community.dto;/*
 *包含样式和元素，传去前端进行展示
 */

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showPrevious;//上一页
    private boolean showNext;//下一页
    private boolean showFirstPage;//首页
    private boolean showEndPage;//尾页
    private List<Integer> pages = new ArrayList<>();
    private Integer page;//当前页数
    private Integer totalPage;//总页数


    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage = totalPage;
        this.page = page;

        pages.add(page);
        //向前先后展示三个页码
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        //当页面不等于第一页时，展示上一页
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        //当页面不为最后一页时，展示下一页
        if (page == totalPage ) {
            showNext = false;
        } else {
            showNext = true;
        }
        //判断展示首页
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        //判断展示尾页
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}
