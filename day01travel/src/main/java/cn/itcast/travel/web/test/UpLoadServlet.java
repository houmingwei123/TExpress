package cn.itcast.travel.web.test;

import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/upLoadServlet")
public class UpLoadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    //上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 ;  // 1MB
    private static final int MAX_FILE_SIZE      = 1024* 200 ; // 200k
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 ; // 1MB
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpLoadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        // 检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }


        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 中文处理
        upload.setHeaderEncoding("UTF-8");

        // 这个路径相对当前应用的目录
        String uploadPath = request.getServletContext().getRealPath("/download/news/images");

        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        System.out.println();

        PrintWriter pw = response.getWriter();
        JSONObject jsonObject = new JSONObject();
        try {
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        //获取文件后缀名
                        String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
                        //判断文件类型
                        if(prefix.equals("jpg") || prefix.equals("gif") || prefix.equals("png") || prefix.equals("jpeg")){
                            //以时间戳+pt两字命名文件
                            long currentTime=System.currentTimeMillis();
                            String filePath = uploadPath  + "\\"+currentTime+"pt."+prefix;
                            File storeFile = new File(filePath);
                            System.out.println(filePath);
                            // 在控制台输出文件的上传路径
                            // 保存文件到硬盘
                            item.write(storeFile);
                            request.setAttribute("message",
                                    "文件上传成功!");
                            jsonObject.put("fileName", currentTime+"pt."+prefix);
                            jsonObject.put("url", filePath);

                            System.out.println("配图上传成功：" + filePath);
                        }else{
                            request.setAttribute("message",
                                    "不支持该文件类型!");
                            jsonObject.put("url", "");
                            System.out.println("配图上传失败,不支持该文件类型!");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message",
                    "错误信息: " + ex.getMessage());
            jsonObject.put("url", "");
        }

        pw.print(jsonObject.toString());
        pw.flush();
        pw.close();

    }

}