package AdbUtilPkg;


public class AdbUtil_test {	
	public class RAWGrayImage 
	{
/*	    // ���� : RAWImage �ҽ� ����� ���� ����ϳ� load(), save() �κп��� ���̰� �ִ�.
	    
	    private String fname;
	    private BufferedImage myBufferedImage = null;
	    private short [][] GrayChannel = null;

	    private int height = 0;
	    private int width = 0;

	    *//**
	     * height, width�� �ݵ�� �˾ƾ� �Ѵ�. raw ������ ����� ���� �����̴�.
	     *//*
	    public RAWGrayImage(String fname, int height, int width) 
	    {              
	        this.fname = fname;
	        this.height = height;
	        this.width = width;
	    }
	    
	    public int getHeight()
	    {
	        return height;    
	    }
	    
	    public int getWidth()
	    {
	        return width;
	    }

	    public short[][] getGrayChannel()
	    {
	        return (short[][])GrayChannel;
	    }

	    public void load() throws ImageGSException
	    {
	        FileInputStream in = null;
	        int length = 0;    
	        int i = 0;
	        int j = 0;        
	        try 
	        {
	            in = new FileInputStream(fname);

	            GrayChannel = InitIMGBuf.ShortIMGBuf(height, width);
	            
	            for(i = 0; i < height; i++)
	            {
	                for(j = 0; j < width; j++)
	                {
	                    GrayChannel[i][j] = (short)in.read();
	                }
	            }
	        
	            in.close();
	        }
	        catch(IOException e)
	        {    
	            if(in != null) try { in.close(); } catch(IOException se) {}
	            
	            throw new ImageGSException(this.getClass().getName() + " >> "
	                                                                + e.getMessage() );    
	        }    
	    }    

	    public void save(String OutFname, short[][] OutGrayChannel, int OutHeight, int OutWidth)
	                            throws ImageGSException
	    {
	        this.fname = OutFname;
	        this.save(OutGrayChannel, OutHeight, OutWidth);    
	    }
	        
	    public void save(short[][] OutGrayChannel, int OutHeight, int OutWidth)
	                                throws ImageGSException
	    {
	        int i, j;
	        FileOutputStream out = null;
	        
	        try 
	        {
	            out = new FileOutputStream(fname);

	            // 2������ 1�������� ��������.
	            for(i=0; i<OutHeight; i++)
	            {
	                for(j=0; j<OutWidth; j++)
	                {
	                    out.write((byte)OutGrayChannel[i][j]);
	                }    
	            }
	            
	            out.close();
	        }
	        catch(Exception e)
	        {
	            if(out != null) try { out.close(); } catch(IOException se) {}
	            throw new ImageGSException(this.getClass().getName() + " >> "
	                                                                + e.getMessage() );    
	        }            
	        
	    }*/
	}
}

