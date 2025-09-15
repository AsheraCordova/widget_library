package com.ashera.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileUtils {
	public static final int DEFAULT_BUFFER_SIZE = 8192;
    public static String readStringFromURL(String requestURL) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new URL(requestURL).openStream());
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
    
    public static String getFileFromClassPath(String fileName) {
    	return convertInputStreamToString(fileName, getInputStreamFromClassPath(fileName));	
    }

    public static InputStream getInputStreamFromClassPath(String fileName) {
		return FileUtils.class.getClassLoader().getResourceAsStream(fileName);
	}
    
    public static URL getFilePathFromClassPath(String fileName) {
    	return FileUtils.class.getClassLoader().getResource(fileName);	
    }
    
	private static String convertInputStreamToString(String fileName, InputStream is)  {
		try (ByteArrayOutputStream result = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = is.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}

			// Java 1.1
			return result.toString(StandardCharsets.UTF_8.name());
		} catch (Exception e) {
			throw new RuntimeException(fileName);
		}

	}


    public static Map<String, String> readFilesContent(Map<String, String> urls) {
        final Map<String, String> resultMap = new HashMap<>();
//        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
//        CountDownLatch countDownLatch = new CountDownLatch(urls.size());
//
//        for (String key : urls.keySet()) {
//            WorkerThread workerThread = new WorkerThread(urls.get(key), countDownLatch, key, resultMap);
//            scheduledThreadPool.schedule(workerThread, 0, TimeUnit.SECONDS);
//        }
//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        scheduledThreadPool.shutdown();
//        while (!scheduledThreadPool.isTerminated()) {
//            // wait for all tasks to finish
//        }
        return resultMap;
    }

	public static String readFileToString(File filePath) throws IOException {
		java.io.Reader input = new java.io.FileReader(filePath);
		return readFileToString(input);	
	}

	private static String readFileToString(java.io.Reader input) throws IOException {
		BufferedReader reader = new BufferedReader(input);
		StringBuilder fileData = new StringBuilder(1000); 
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		} 
 
		reader.close();
 
		return  fileData.toString();
	}
	
	public static java.util.Properties loadPropertiesFromClassPath(String fileName) {		
		try {
			String fileContent = readFileToString(new InputStreamReader(getInputStreamFromClassPath(fileName)));
			return loadProperties(fileContent);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static java.util.Properties loadProperties(String fileContent) {
		java.io.StringReader stringReader = null;

		try {
			stringReader = new java.io.StringReader(fileContent);
			java.util.Properties properties = new java.util.Properties();
			properties.load(stringReader);

			return properties;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (stringReader != null) {
				stringReader.close();
			}
		}
	}
	
	public static java.util.Properties getFileAsProperties(String fileName) {
		java.util.Properties properties = new java.util.Properties();
		java.io.StringReader stringReader = null;
		try {
			String fileContent = FileUtils.readFileToString(new File(fileName));
			stringReader = new java.io.StringReader(fileContent);

			try {
				properties.load(stringReader);
			} catch (java.io.IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (stringReader != null) {
				stringReader.close();
			}
		}
		return properties;
	}
	
	public static List<File> getFilenamesForDirnameFromCP(String directoryName) {
	    try {
			List<File> filenames = new ArrayList<>();

			URL url = Thread.currentThread().getContextClassLoader().getResource(directoryName);
			if (url != null) {
			    if (url.getProtocol().equals("file")) {
			        File file = Paths.get(url.toURI()).toFile();
			        if (file != null) {
			            File[] files = file.listFiles();
			            if (files != null) {
			                for (File filename : files) {
			                    filenames.add(filename);
			                }
			            }
			        }
			    }
			}
			return filenames;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static byte[] readAllBytes(InputStream inputStream) {
		final int bufLen = 4 * 0x400; // 4KB
		byte[] buf = new byte[bufLen];
		int readLen;

		try {
			try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
				while ((readLen = inputStream.read(buf, 0, bufLen)) != -1)
					outputStream.write(buf, 0, readLen);

				return outputStream.toByteArray();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
			}
		}
	}

	public static String getSlashAppendedDirectoryName(String directoryName) {
		return directoryName.endsWith("/") ? directoryName : directoryName + "/";
	}
}
