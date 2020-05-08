package page;

import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.pojo.Employee;
import org.service.EmployService;

import java.util.List;

/**
 * @author yu
 * @date 2020/5/8
 */
public class pageTest {
    private   EmployService employService = new EmployService();
    @Test
    public void pageOne(){
        int pageNum = 2;
        int pageSize = 3;
        List<Employee> employees = employService.getEmpByCustom(pageNum, pageSize);
        //得到所有的数据总数
        int count = employService.getEmpCount();
        //上一页
        int prev = pageNum -1;
        //下一页
        int next = pageNum + 1;
        //得到总页数
        int pages = (int) Math.ceil(count * 1.0 / pageSize);
        //最后一页
        int last = pages;
    }

    @Test
    public void getEmpByRowBounds(){
        RowBounds rowBounds = new RowBounds(4, 2);
        List<Employee> employees = employService.getEmpByRowBounds(rowBounds);
    }

    @Test
    public void getEmpByPageHelper(){
        int pageNum = 1;
        int pageSize = 3;
        List<Employee> employees = employService.getEmpByPageHelper(pageNum, pageSize);
    }
}
