// **********************************************************************
//
// Copyright (c) 2003-2015 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.6.1
//
// <auto-generated>
//
// Generated from file `FileSystem.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.joken.FileSystem.rpc;

/**
 * 上传文件模型
 *
 **/
public class UploadModel implements java.lang.Cloneable, java.io.Serializable
{
    /**
     * 文件扩展名
     **/
    public String extension;

    /**
     * 文件扩展名
     **/
    public String
    getExtension()
    {
        return extension;
    }

    /**
     * 文件扩展名
     **/
    public void
    setExtension(String _extension)
    {
        extension = _extension;
    }

    /**
     * 二进制文件内容
     **/
    public byte[] buffer;

    /**
     * 二进制文件内容
     **/
    public byte[]
    getBuffer()
    {
        return buffer;
    }

    /**
     * 二进制文件内容
     **/
    public void
    setBuffer(byte[] _buffer)
    {
        buffer = _buffer;
    }

    public byte
    getBuffer(int _index)
    {
        return buffer[_index];
    }

    public void
    setBuffer(int _index, byte _val)
    {
        buffer[_index] = _val;
    }

    public UploadModel()
    {
        extension = "";
    }

    public UploadModel(String extension, byte[] buffer)
    {
        this.extension = extension;
        this.buffer = buffer;
    }

    public boolean
    equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        UploadModel _r = null;
        if(rhs instanceof UploadModel)
        {
            _r = (UploadModel)rhs;
        }

        if(_r != null)
        {
            if(extension != _r.extension)
            {
                if(extension == null || _r.extension == null || !extension.equals(_r.extension))
                {
                    return false;
                }
            }
            if(!java.util.Arrays.equals(buffer, _r.buffer))
            {
                return false;
            }

            return true;
        }

        return false;
    }

    public int
    hashCode()
    {
        int __h = 5381;
        __h = IceInternal.HashUtil.hashAdd(__h, "::com::joken::FileSystem::rpc::UploadModel");
        __h = IceInternal.HashUtil.hashAdd(__h, extension);
        __h = IceInternal.HashUtil.hashAdd(__h, buffer);
        return __h;
    }

    public UploadModel
    clone()
    {
        UploadModel c = null;
        try
        {
            c = (UploadModel)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void
    __write(IceInternal.BasicStream __os)
    {
        __os.writeString(extension);
        FileBufferHelper.write(__os, buffer);
    }

    public void
    __read(IceInternal.BasicStream __is)
    {
        extension = __is.readString();
        buffer = FileBufferHelper.read(__is);
    }

    static public void
    __write(IceInternal.BasicStream __os, UploadModel __v)
    {
        if(__v == null)
        {
            __nullMarshalValue.__write(__os);
        }
        else
        {
            __v.__write(__os);
        }
    }

    static public UploadModel
    __read(IceInternal.BasicStream __is, UploadModel __v)
    {
        if(__v == null)
        {
             __v = new UploadModel();
        }
        __v.__read(__is);
        return __v;
    }
    
    private static final UploadModel __nullMarshalValue = new UploadModel();

    public static final long serialVersionUID = -1885370613L;
}