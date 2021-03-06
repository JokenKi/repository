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
// Generated from file `BaseException.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.joken.common.rpc;

/**
 * 基础的异常定义。
 *
 **/
public class BaseException extends Ice.UserException
{
    public BaseException()
    {
        reason = "";
        userMessage = "";
    }

    public BaseException(Throwable __cause)
    {
        super(__cause);
        reason = "";
        userMessage = "";
    }

    public BaseException(String reason, String userMessage)
    {
        this.reason = reason;
        this.userMessage = userMessage;
    }

    public BaseException(String reason, String userMessage, Throwable __cause)
    {
        super(__cause);
        this.reason = reason;
        this.userMessage = userMessage;
    }

    public String
    ice_name()
    {
        return "com::joken::common::rpc::BaseException";
    }

    /**
     * 出异常原因。
     *
     **/
    public String reason;

    /**
     * 呈现给用户的异常信息。
     *
     **/
    public String userMessage;

    protected void
    __writeImpl(IceInternal.BasicStream __os)
    {
        __os.startWriteSlice("::com::joken::common::rpc::BaseException", -1, true);
        __os.writeString(reason);
        __os.writeString(userMessage);
        __os.endWriteSlice();
    }

    protected void
    __readImpl(IceInternal.BasicStream __is)
    {
        __is.startReadSlice();
        reason = __is.readString();
        userMessage = __is.readString();
        __is.endReadSlice();
    }

    public static final long serialVersionUID = -1788889652L;
}
