package life.majiang.community.DataTransferObject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zx on 2020/6/27 22:36
 */
@Data
public class PaginationDTO<T> {
    private List<T> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private List<Integer> pages = new ArrayList<>();//页码数组
    private Integer page;//当前页？
    private Integer totalPage=0;


    public void setPagination(Integer totalCount, Integer page, Integer size) {
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        this.page = page;
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }
        //是否展示上一页标志
        if (this.page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        //是否展示下页标签
        if (this.page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }
        //是否展示第一页标记
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        //是否展示最后一页跳转，看不见最后一页时
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}
