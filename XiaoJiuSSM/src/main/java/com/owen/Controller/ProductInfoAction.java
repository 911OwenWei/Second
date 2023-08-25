package com.owen.Controller;

import com.github.pagehelper.PageInfo;
import com.owen.Service.ProductInfoService;
import com.owen.pojo.ProductInfo;
import com.owen.pojo.vo.productInfoVo;
import com.owen.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {

    String saveFileName="";

    @Autowired
    ProductInfoService  productInfoService;

    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list",list);
        return "product";
    }

    @RequestMapping("/split")
    public String spilt(HttpServletRequest request){
        PageInfo info=null;
        Object vo = request.getSession().getAttribute("prodVo");

        if (vo!=null){
            info=productInfoService.spiltAndMulCon((productInfoVo) vo,5);
            request.getSession().removeAttribute("prodVo");
        }else {
             info=productInfoService.spiltPage(1,5);
        }

        request.setAttribute("info",info);
        return "product";
    }

    @ResponseBody
    @RequestMapping("/ajaxSplit")
    public void ajaxSplit(productInfoVo vo, HttpSession session){
        PageInfo info=productInfoService.spiltAndMulCon(vo,5);
        session.setAttribute("info",info);
    }


    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage,HttpServletRequest request) throws IOException {
        saveFileName=FileNameUtil.getUUIDFileName()+ FileNameUtil.getFileType(pimage.getOriginalFilename());
        String path = request.getServletContext().getRealPath("/image_big");
        pimage.transferTo(new File(path+File.separator+saveFileName));

        JSONObject object = new JSONObject();
        object.put("imgurl",saveFileName);

        return object.toString();
    }


    @RequestMapping("/save")
    public String save(ProductInfo productInfo,HttpServletRequest request){
        productInfo.setpImage(saveFileName);
        productInfo.setpDate(new Date());

        int num;
        num=productInfoService.save(productInfo);
        if (num>0) {
            request.setAttribute("msg","增加成功");
        }else {
            request.setAttribute("msg","增加失败");
        }
        saveFileName="";
        return "forward:/prod/split.action";
    }

    @RequestMapping("/one")
    public String one(int pid,productInfoVo vo, Model model,HttpSession session){
        ProductInfo info=productInfoService.getById(pid);

        model.addAttribute("prod",info);
        session.setAttribute("prodVo",vo);
        return "update";
    }

    @RequestMapping("/update")
    public String update(ProductInfo info,HttpServletRequest request){
        if (!saveFileName.equals("")) {
            info.setpImage(saveFileName);
        }
        int num=-1;
        num=productInfoService.updateProduct(info);
        if (num>0) {
            request.setAttribute("msg","更新成功！");
        }else {
            request.setAttribute("msg","更新失败！");
        }
        saveFileName="";
        return "forward:/prod/split.action";
    }


    @RequestMapping("/delete")
    public String del(productInfoVo vo,int pid,HttpServletRequest request){
        int num=-1;

        num = productInfoService.delete(pid);

        if (num>0) {
            request.setAttribute("msg","删除成功");
            request.getSession().setAttribute("deleteVo",vo);
        }else {
            request.setAttribute("msg","删除失败 ");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit",produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request){
        PageInfo info=null;

        Object vo = request.getSession().getAttribute("deleteVo");
        if (vo!=null) {
            info = productInfoService.spiltAndMulCon((productInfoVo) vo, 5);
        }else
        {
            info=productInfoService.spiltPage(1,5);
        }
        request.getSession().setAttribute("info",info);
        return request.getAttribute("msg");
    }


    @RequestMapping("/deleteBath")
    public String deleteBatch(String pids,HttpServletRequest request){
        String[] ids = pids.split(",");
        int num=-1;
        num = productInfoService.deleteBatch(ids);

        if (num>0) {
            request.setAttribute("msg","删除成功！");
        }
        else {
            request.setAttribute("msg","删除失败！");
        }
        return "forward:/prod/deleteAjaxSplit.action";
    }

    @ResponseBody
    @RequestMapping("condition")
    public void selectByMulCon(productInfoVo productInfoVo,HttpSession session){
        List<ProductInfo> list = productInfoService.selectByMulCon(productInfoVo);
        session.setAttribute("list",list);
    }
}
