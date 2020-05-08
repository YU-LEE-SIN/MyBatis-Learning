package org.pojo;

/**
 * @author yu
 * @date 2020/4/11
 */
public class Employee {
    private Integer id;
    private String name;
    private Integer age;
    private String address;
    private Integer deptId;

    public Employee() { }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Employee(String name, Integer age,String address, Integer deptId) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.deptId = deptId;
    }

    public Employee(String name,Integer id) {
        this.name = name;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", deptId=" + deptId +
                '}';
    }
}
