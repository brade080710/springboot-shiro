package com.xm.shiro.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.springframework.web.multipart.MultipartFile;


public class FileUtil {
	/**
	 * 复制文件或者目录,复制前后文件完全一样。
	 * 
	 * @param resFilePath
	 *            源文件路径
	 * @param distFolder
	 *            目标文件夹
	 * @IOException 当操作发生异常时抛出
	 */
	public static void copyFile(String resFilePath, String distFolder) throws IOException {
		File resFile = new File(resFilePath);
		File distFile = new File(distFolder);
		if (resFile.isDirectory()) {
			FileUtils.copyDirectoryToDirectory(resFile, distFile);
		} else if (resFile.isFile()) {
			FileUtils.copyFileToDirectory(resFile, distFile, true);
		}
	}

	/**
	 * 删除一个文件或者目录
	 * 
	 * @param targetPath
	 *            文件或者目录路径
	 * @IOException 当操作发生异常时抛出
	 */
	public static boolean deleteFile(String targetPath) throws IOException {
		File targetFile = new File(targetPath);
		if (targetFile.isDirectory()) {
			FileUtils.deleteDirectory(targetFile);
			return true;
		} else if (targetFile.exists() && targetFile.isFile()) {
			return targetFile.delete();
		}
		return false;
	}

	/**
	 * 删除目录及目录下的文件
	 * 
	 * @param dir
	 *            要删除的目录路径
	 * @return 删除成功返回true，否则返回false
	 * @throws IOException
	 */
	public static boolean deleteDirectory(String dir) throws IOException {
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		if ((!dirFile.exists()) || (!dirFile.isDirectory()))
			return false;
		boolean flag = true;
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} else if (files[i].isDirectory()) {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			return false;
		}
		return dirFile.delete();
	}

	/**
	 * 重命名文件或文件夹
	 * 
	 * @param resFilePath
	 *            源文件路径
	 * @param newFileName
	 *            重命名
	 * @return 操作成功标识
	 */
	public static boolean renameFile(String resFilePath, String newFileName) {
		String newFilePath = StringUtils.formatPath(getParentPath(resFilePath) + "/" + newFileName);
		File resFile = new File(resFilePath);
		File newFile = new File(newFilePath);
		return resFile.renameTo(newFile);
	}

	/**
	 * 获取文件父路径
	 *
	 * @param path
	 *            文件路径
	 * @return 文件父路径
	 */
	public static String getParentPath(String path) {
		return new File(path).getParent();
	}

	/**
	 * 读取文件或者目录的大小
	 * 
	 * @param distFilePath
	 *            目标文件或者文件夹
	 * @return 文件或者目录的大小，如果获取失败，则返回-1
	 */
	public static long genFileSize(String distFilePath) {
		File distFile = new File(distFilePath);
		if (distFile.isFile()) {
			return distFile.length();
		} else if (distFile.isDirectory()) {
			return FileUtils.sizeOfDirectory(distFile);
		}
		return -1L;
	}

	/**
	 * 判断一个文件是否存在
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 存在返回true，否则返回false
	 */
	public static boolean isExist(String filePath) {
		if (filePath == null || filePath.equals(""))
			return false;
		return new File(filePath).exists();
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileSuffix(String filePath) {
		String ext = "";
		File file = new File(filePath);
		if (file.exists()) {
			Map<String, String> suffix = FileUtil.parsePath(file.getName());
			ext = suffix.get("ext");
		}
		return ext;
	}

	/**
	 * 本地某个目录下的文件列表（不递归）
	 * 
	 * @param folder
	 *            ftp上的某个目录
	 * @param suffix
	 *            文件的后缀名（比如.mov.xml)
	 * @return 文件名称列表
	 */
	public static String[] listFilebySuffix(String folder, String suffix) {
		IOFileFilter fileFilter1 = new SuffixFileFilter(suffix);
		IOFileFilter fileFilter2 = new NotFileFilter(DirectoryFileFilter.INSTANCE);
		FilenameFilter filenameFilter = new AndFileFilter(fileFilter1, fileFilter2);
		return new File(folder).list(filenameFilter);
	}

	public static String getFilePathBySuffix(String folder, String suffix) {
		File file = new File(folder);
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			File tempFile = files[i];
			if (tempFile.getName().endsWith(suffix))
				return tempFile.getAbsolutePath();
		}
		return null;
	}

	public static String getFilePathBySuffix(String folder, List<String> sufixs) {
		File file = new File(folder);// 文件夹路径
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				File tempFile = files[i];
				for (String suffix : sufixs) {
					String fileName = tempFile.getAbsolutePath();
					if (fileName.endsWith(suffix))
						return fileName;
				}
			}
		}
		return null;
	}

	/**
	 * 某个目录下的文件列表
	 * 
	 * @param folder
	 * @param suffix
	 *            后缀名（比如.xml)
	 * @return 全路径文件名
	 */
	public static List<String> getFileNamesBySuffix(String folder, String suffix) {
		List<String> result = new ArrayList<String>();

		try {
			File file = new File(folder);
			File[] files = file.listFiles();

			for (int i = 0; i < files.length; i++) {
				File tempFile = files[i];
				if (tempFile.isDirectory()) {
					String subFolder = tempFile.getPath();
					List<String> list = getFileNamesBySuffix(subFolder, suffix);
					for (int j = 0; j < list.size(); j++) {
						result.add(list.get(j));
					}
				} else {
					if (!tempFile.isFile()) {
						continue;
					}
					if (tempFile.getName().endsWith(suffix))
						result.add(tempFile.getPath());
				}
				if (i == (files.length - 1)) {
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	public static List<String> getFileNamesByFolder(String folder) {
		List<String> result = new ArrayList<String>();

		try {
			File file = new File(folder);
			File[] files = file.listFiles();

			for (int i = 0; i < files.length; i++) {
				File tempFile = files[i];
				if (!tempFile.isFile())
					continue;
				result.add(tempFile.getPath());
				if (i == (files.length - 1))
					return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	/**
	 * 某个目录下的文件列表
	 * 
	 * @param folder
	 * @param suffix
	 *            后缀名（比如.xml)
	 * @return list <File>
	 */
	public static List<File> getFilesBySuffix(String folder, String suffix) {
		List<File> result = new ArrayList<File>();

		try {
			File file = new File(folder);
			File[] files = file.listFiles();

			for (int i = 0; i < files.length; i++) {
				File tempFile = files[i];
				if (tempFile.isDirectory()) {
					String subFolder = tempFile.getPath();
					List<File> list = getFilesBySuffix(subFolder, suffix);
					for (int j = 0; j < list.size(); j++) {
						result.add(list.get(j));
					}
				} else {
					if (!tempFile.isFile()) {
						continue;
					}
					if (tempFile.getName().endsWith(suffix))
						result.add(tempFile);
				}
				if (i == (files.length - 1)) {
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	public static List<File> getFilesBySuffix(String suffix, String... folders) {
		List<File> result = new ArrayList<File>();

		try {
			for (String folder : folders) {
				File file = new File(folder);
				File[] files = file.listFiles();

				for (int i = 0; i < files.length; i++) {
					File tempFile = files[i];
					if (tempFile.isDirectory()) {
						String subFolder = tempFile.getPath();
						List<File> list = getFilesBySuffix(subFolder, suffix);
						for (int j = 0; j < list.size(); j++) {
							result.add(list.get(j));
						}
					} else {
						if (!tempFile.isFile()) {
							continue;
						}
						if (tempFile.getName().endsWith(suffix))
							result.add(tempFile);
					}
					if (i == (files.length - 1)) {
						return result;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	/**
	 * 将字符串写入指定文件(当指定的父路径中文件夹不存在时，会最大限度去创建，以保证保存成功！)
	 * 
	 * @param res
	 *            原字符串
	 * @param filePath
	 *            文件路径
	 * @return 成功标记
	 */
	public static boolean string2File(String res, String filePath) {
		boolean flag = true;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			File distFile = new File(filePath);
			if (!distFile.getParentFile().exists())
				distFile.getParentFile().mkdirs();
			bufferedReader = new BufferedReader(new StringReader(res));
			bufferedWriter = new BufferedWriter(new FileWriter(distFile));
			char buf[] = new char[1024]; // 字符缓冲区
			int len;
			while ((len = bufferedReader.read(buf)) != -1) {
				bufferedWriter.write(buf, 0, len);
			}
			bufferedWriter.flush();
			bufferedReader.close();
			bufferedWriter.close();
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	static public boolean createDirs(String dirs) {
        if (dirs == null || dirs.length() < 1)
            return false;

        
            File path = new File(dirs);
            
            if (!path.exists()) {
                return path.mkdirs();
            } else
                return true;

    }

	
	/**
	 * 逐级创建目录
	 * @param dirs
	 * @param otherWritable 如果为true 则修改新建目录的权限为其他人可写
	 * @return
	 */
	static public boolean createDirs(String dirs, boolean otherWritable) {
		if (dirs == null || dirs.length() < 1)
			return false;

		if(otherWritable){
		    String sep = File.separator;
		    String path = new File(dirs).getAbsolutePath();
		    int pos = path.indexOf(sep, 1);
		    //create dir and set permission
		    while(pos != -1){
		        File base = new File(path.substring(0, pos));

                if (!base.exists()) {
                    base.mkdirs();
                    base.setWritable(true, false);
                }
                pos = path.indexOf(sep, pos+1);
                if(pos==-1){
                    if(base.getAbsolutePath().length()<path.length()){
                        pos = path.length();
                    }
                }
		        
            }

		    return true;
		    
		}else{
            File path = new File(dirs);
		    
            if (!path.exists()) {
                return path.mkdirs();
            } 
            return true;
		}

	}

	// 获取一个散列路径，返回的路径形式为 "xx/xx/"
	static public String getHashPath(String depathAndRange) {

		String[] drs = depathAndRange.split("\\*");
		int depth = Integer.parseInt(drs[0]);
		int range = Integer.parseInt(drs[1]);

		return getHashPath(depth, range);
	}

	// 获取一个散列路径，返回的路径形式为 "xx/xx/"
	static public String getHashPath(int depth, int range) {
		depth = (depth < 1) ? 1 : depth;
		range = (range < 1) ? 1 : range;
		Integer randNum = 0;
		String path = "";
		Random rand = new Random(System.currentTimeMillis());
		for (int i = 0; i < depth; i++) {
			randNum = rand.nextInt(range);
			path += (randNum.toString() + "/");
		}
		return path;
	}

	public static byte[] readFile(String realFile) throws IOException {
		File file = new File(realFile);
		FileInputStream stream = new FileInputStream(file);

		return IOUtils.toByteArray(stream);
	}

	/**
	 * 创建内存zip文件
	 * 
	 * @param files
	 *            读取被压缩的文件
	 */
	public static byte[] createZipFileInMemory(List<String> files) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		ZipOutputStream zos = new ZipOutputStream(baos);
		for (String fileName : files) {

			File file = new File(fileName);
			String name = file.getName();
			if (!file.exists())
				throw new FileNotFoundException();

			FileInputStream stream = new FileInputStream(file);
			zos.putNextEntry(new ZipEntry(name));
			zos.write(IOUtils.toByteArray(stream));
			zos.flush();
			zos.closeEntry();
			stream.close();
		}
		zos.close();
		return baos.toByteArray();
		// return baos;
	}

	public static void writeZipFile(byte[] data, String entryName, String destPath, boolean overrite) throws Exception {
		File file = new File(destPath);
		if (file.exists()) {
			if (!overrite) {
				return;
			} else {
				file.delete();
			}
		}

		FileUtil.createDirs(FileUtil.dirname(destPath));

		int p = entryName.lastIndexOf(".");
		if (p != -1) {
			entryName = "1" + entryName.substring(p);
		}

		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destPath));
		zos.putNextEntry(new ZipEntry(entryName));
		zos.write(data);
		zos.flush();
		zos.closeEntry();
		zos.close();
	}

	public static String dirname(String path) {
		if (path == null)
			return null;

		File file = new File(path);
		return file.getParent();
		// String path = file.getAbsolutePath().replaceAll(file.getName(),
		// "").trim();
		// FileUtil.createDirs(path);
	}

	public static boolean writeFile(String realFilePath, byte[] bData) {
		try {
			File file = new File(realFilePath);
			file.createNewFile();
			FileOutputStream fs = new FileOutputStream(realFilePath);
			fs.write(bData);
			fs.flush();
			fs.close();
		} catch (Exception e) {
			// log.info("重写文件时失败。");
			return false;
		}
		return true;
	}

	/**
	 * 磁盘写文件 如果文件存在，就删除掉。 创建新文件。
	 * 
	 * @param realFilePath
	 * @param bData
	 * @return
	 * @throws HansonException
	 */
	public static boolean myWriteFile(String realFilePath, byte[] bData) throws Exception {
		File file = new File(realFilePath);
		String path = file.getAbsolutePath().replaceAll(file.getName(), "").trim();
		FileUtil.createDirs(path);
		if (file.exists())
			file.delete();
		file.createNewFile();
		FileOutputStream fs = new FileOutputStream(realFilePath);
		fs.write(bData);
		fs.close();
		return true;
	}

	/**
	 * 分析路径字符串，柝分出路径，文件名，名字，扩展名。
	 * 
	 * @param pathname
	 *            路径字符串
	 * @return
	 */
	static public HashMap<String, String> parsePath(String pathname) {

		if (pathname == null || pathname.length() < 1)
			return null;
		HashMap<String, String> map = new HashMap<String, String>();
		if (pathname.endsWith("/") || pathname.endsWith("\\")) {
			// 字符串是一个路径
			map.put("path", pathname);
		} else {
			// 查找文件名
			String filename = null;
			int pos = pathname.lastIndexOf('/');
			if (pos > -1)
				filename = pathname.substring(pos + 1);
			else {
				pos = pathname.lastIndexOf('\\');
				if (pos > -1)
					filename = pathname.substring(pos + 1);
				else
					filename = pathname; // 没有找到路径分隔符，说明整个字符串是一个文件名
			}
			// 提取路径
			if (pos > -1)
				map.put("path", pathname.substring(0, pos + 1));

			if (filename != null) {
				map.put("filename", filename);
				// 提取扩展名
				pos = filename.lastIndexOf('.');
				if (pos > -1)
					map.put("ext", filename.substring(pos + 1));

				// 提取文件名（不含扩展名）
				if (pos > -1)
					map.put("name", filename.substring(0, pos));
			}
		}
		return map;
	}

	/**
	 * @descrption 验证文件格式，这里主要验证后缀名
	 * @author yp.wu
	 * @create 2013-06-08下午14:08:12
	 * @param file
	 *            MultipartFile对象
	 * @param maxLength
	 *            文件最大限制
	 * @param allowExtName
	 *            不允许上传的文件扩展名
	 * @return 文件格式是否合法 jpg or jpge or png
	 */
	public static boolean validateFile(MultipartFile file, long maxLength, String[] allowExtName) {
		if (file.getSize() < 0 || file.getSize() > maxLength)
			return false;
		String filename = file.getOriginalFilename();

		// 处理不选择文件点击上传时，也会有MultipartFile对象，在此进行过滤
		if (filename == "") {
			return false;
		}
		String extName = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
		if (allowExtName == null || allowExtName.length == 0 || inArray(allowExtName, extName)) {

			return true;
		} else {
			return false;
		}
	}

	public static boolean inArray(String[] a, String obj) {
		for (String s : a) {
			if (s == null) {
				if (obj == null)
					return true;
				continue;
			} else if (obj == null) {
				return false;
			} else if (s.equals(obj)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 指定目录和文件后缀名数组,查找存在的文件名
	 * 
	 * @param dir
	 * @param suffix
	 * @return 例如:String[] {"xls", "xlsx"}
	 */
	public static String getDirFileName(String dir, String[] suffix) {
		String fileName = "";

		File file = new File(dir);// 文件夹路径
		String[] str = null;
		if (file.isDirectory()) {
			str = file.list();
			for (int i = 0; i < str.length; i++) {
				String s = str[i];
				for (int j = 0; j < suffix.length; j++) {
					if (s.indexOf(suffix[j]) != -1)
						fileName = str[i];
				}
			}
		}
		return fileName;
	}

	public static String getFileName(String filePath) {
		File file = new File(filePath);
		return file.getName();
	}

	public static String join(String... paths) {
		String[] list = new String[paths.length];
		int i = 0;
		for (String p : paths) {
			list[i++] = StringUtils.rtrim(p, "/");
		}
		return StringUtils.join(list, "/");
	}
}
