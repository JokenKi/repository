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
 * 基础的错误定义。
 *
 **/
public class BaseError extends Ice.UserException
{
    public BaseError()
    {
        reason = "";
        userMessage = "";
    }

    public BaseError(Throwable __cause)
    {
        super(__cause);
        reason = "";
        userMessage = "";
    }

    public BaseError(String reason, String userMessage)
    {
        this.reason = reason;
        this.userMessage = userMessage;
    }

    public BaseError(String reason, String userMessage, Throwable __cause)
    {
        super(__cause);
        this.reason = reason;
        this.userMessage = userMessage;
    }

    public String
    ice_name()
    {
        return "com::joken::common::rpc::BaseError";
    }

    /**
     * 出错误原因。
     *
     **/
    public String reason;

    /**
     * 呈现给用户的错误信息。
     *
     **/
    public String userMessage;

    protected void
    __writeImpl(IceInternal.BasicStream __os)
    {
        __os.startWriteSlice("::com::joken::common::rpc::BaseError", -1, true);
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

    public static final long serialVersionUID = -1038451645L;
}