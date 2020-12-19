package com.example.demo.com.example;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
class CalController {
    @RequestMapping("/cal")
    public Result<result1> cal(@RequestParam("formula") String formula){
        CalculatorImp1 a=new CalculatorImp1(formula);
        result1 b=new result1(a.deal_bracket(formula));
        return Result.Success(b);
    }
}
@Data
class result1{
    String result;
    result1(String a){
        result=a;
    }
}
class Result<T> {
    public int code;
    public String msg;
    public T data;

    public Result( ResponseCode RC ,T data){
        this.code = RC.code;
        this.msg = RC.msg;
        this.data = data;
    }


    public static <T> Result<T> Success(T data){
        return new Result<T>(ResponseCode.SERVER_SUCCESS,data);
    }

    public static <T> Result<T> Error(T data){
        return new Result<T>(ResponseCode.SERVER_ERROR,data);
    }

}
class ResponseCode {
    public int code;
    public String msg;

    public ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public  static ResponseCode SERVER_SUCCESS = new ResponseCode(200,"SUCCESS");
    public  static ResponseCode SERVER_ERROR = new ResponseCode(100,"ERROR");
}
class CalculatorImp1 {
    String text;

    public CalculatorImp1(String a) {
        text = a;
    }

    public String deal_bracket(String exp) {
        String new_exp, res;
        String exp1 = exp;
        String regex = "\\([^()]+\\)";
        Pattern pattern = Pattern.compile(regex);
        while (true) {
            Matcher m = pattern.matcher(exp1);
            if (m.find()) {
                new_exp = m.group();
                res = calculate(new_exp);
                exp1 = exp1.replace(new_exp, res);
            } else {
                res = calculate(exp1);
                return res;
            }
        }
    }

    public String calculate(String exp) {
        String exp1 = exp.replace("(", "");
        String exp2 = exp1.replace(")", "");
        String new_exp, res;
        String regex = "-?\\d+[*/]-?\\d+";
        Pattern pattern = Pattern.compile(regex);
        while (true) {
            Matcher m = pattern.matcher(exp2);
            if (m.find()) {
                new_exp = m.group();
                res = calculate_mul(new_exp);
                exp2 = exp2.replace(new_exp, res);
            } else {
                return calculate_add(exp2);
            }
        }
    }

    private String calculate_mul(String exp) {
        if (Pattern.compile("-?\\d+[*]-?\\d+").matcher(exp).find()) {
            String[] a = exp.split("[*]");
            int num1 = Integer.parseInt(a[0]);
            int num2 = Integer.parseInt(a[1]);
            return String.valueOf(num1 * num2);
        } else {
            String[] a = exp.split("[/]");
            int num1 = Integer.parseInt(a[0]);
            int num2 = Integer.parseInt(a[1]);
            return String.valueOf(num1 / num2);
        }
    }

    private String calculate_add(String s) {

        if (s.charAt(0) == '-') {
            String b = "0";
            s = b.concat(s);
        }
        int n = s.length();
        int sum = 0;
        String str="";
        for(int i=0;i<n;i++){
            char ch=s.charAt(i);
            if(ch>='0'&&ch<='9'){
                str += ch;
            }else if(ch=='+'||ch=='-'){
                sum+=Integer.parseInt(str);
                str=""+ch;
            }
            else if(ch==' ')
                continue;
        }
        if(str!=""){
            sum+=Integer.parseInt(str);
        }
        return String.valueOf(sum);
    }
}