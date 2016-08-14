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
// Generated from file `ResponseModel.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.joken.common.rpc;

/**
 * 分页实体类
 **/
public class ResponseModel implements java.lang.Cloneable, java.io.Serializable
{
    /**
     * 执行成功状态
     *
     **/
    public boolean success;

    /**
     * 执行成功状态
     *
     **/
    public boolean
    getSuccess()
    {
        return success;
    }

    /**
     * 执行成功状态
     *
     **/
    public void
    setSuccess(boolean _success)
    {
        success = _success;
    }

    public boolean
    isSuccess()
    {
        return success;
    }

    /**
     * 总页数
     *
     **/
    public int status;

    /**
     * 总页数
     *
     **/
    public int
    getStatus()
    {
        return status;
    }

    /**
     * 总页数
     *
     **/
    public void
    setStatus(int _status)
    {
        status = _status;
    }

    /**
     * 返回字符串信息
     *
     **/
    public String msg;

    /**
     * 返回字符串信息
     *
     **/
    public String
    getMsg()
    {
        return msg;
    }

    /**
     * 返回字符串信息
     *
     **/
    public void
    setMsg(String _msg)
    {
        msg = _msg;
    }

    public ResponseModel()
    {
        msg = "";
    }

    public ResponseModel(boolean success, int status, String msg)
    {
        this.success = success;
        this.status = status;
        this.msg = msg;
    }

    public boolean
    equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        ResponseModel _r = null;
        if(rhs instanceof ResponseModel)
        {
            _r = (ResponseModel)rhs;
        }

        if(_r != null)
        {
            if(success != _r.success)
            {
                return false;
            }
            if(status != _r.status)
            {
                return false;
            }
            if(msg != _r.msg)
            {
                if(msg == null || _r.msg == null || !msg.equals(_r.msg))
                {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public int
    hashCode()
    {
        int __h = 5381;
        __h = IceInternal.HashUtil.hashAdd(__h, "::com::joken::common::rpc::ResponseModel");
        __h = IceInternal.HashUtil.hashAdd(__h, success);
        __h = IceInternal.HashUtil.hashAdd(__h, status);
        __h = IceInternal.HashUtil.hashAdd(__h, msg);
        return __h;
    }

    public ResponseModel
    clone()
    {
        ResponseModel c = null;
        try
        {
            c = (ResponseModel)super.clone();
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
        __os.writeBool(success);
        __os.writeInt(status);
        __os.writeString(msg);
    }

    public void
    __read(IceInternal.BasicStream __is)
    {
        success = __is.readBool();
        status = __is.readInt();
        msg = __is.readString();
    }

    static public void
    __write(IceInternal.BasicStream __os, ResponseModel __v)
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

    static public ResponseModel
    __read(IceInternal.BasicStream __is, ResponseModel __v)
    {
        if(__v == null)
        {
             __v = new ResponseModel();
        }
        __v.__read(__is);
        return __v;
    }
    
    private static final ResponseModel __nullMarshalValue = new ResponseModel();

    public static final long serialVersionUID = 523249141L;
}