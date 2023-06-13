package com.etjava.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
@Controller
public class FileController {
    //@RequestParam("file") ��name=file�ؼ��õ����ļ���װ��CommonsMultipartFile ����
    //�����ϴ�CommonsMultipartFile��Ϊ���鼴��
    @RequestMapping("/upload")
    public String fileUpload(@RequestParam("file") CommonsMultipartFile file , HttpServletRequest request) throws IOException {
        //��ȡ�ļ��� : file.getOriginalFilename();
        String uploadFileName = file.getOriginalFilename();
        //����ļ���Ϊ�գ�ֱ�ӻص���ҳ��
        if ("".equals(uploadFileName)){
            return "redirect:/show.jsp";
        }
        System.out.println("�ϴ��ļ��� : "+uploadFileName);
        //�ϴ�·����������
        String path = request.getServletContext().getRealPath("/upload");
        //���·�������ڣ�����һ��
        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdir();
        }
        System.out.println("�ϴ��ļ������ַ��"+realPath);
        InputStream is = file.getInputStream(); //�ļ�������
        OutputStream os = new FileOutputStream(new File(realPath,uploadFileName)); //�ļ������
        //��ȡд��
        int len=0;
        byte[] buffer = new byte[1024];
        while ((len=is.read(buffer))!=-1){
            os.write(buffer,0,len);
            os.flush();
        }
        os.close();
        is.close();
        return "redirect:/index.jsp";
    }

    /*
     * ʹ��file.Transto �������ϴ����ļ�
     */
    @RequestMapping("/upload2")
    public String  fileUpload2(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) throws IOException {
        //�ϴ�·����������
        String path = request.getServletContext().getRealPath("/upload");
        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdir();
        }
        //�ϴ��ļ���ַ
        System.out.println("�ϴ��ļ������ַ��"+realPath);
        //ͨ��CommonsMultipartFile�ķ���ֱ��д�ļ���ע�����ʱ��
        file.transferTo(new File(realPath +"/"+ file.getOriginalFilename()));
        return "redirect:/index.jsp";
    }
}