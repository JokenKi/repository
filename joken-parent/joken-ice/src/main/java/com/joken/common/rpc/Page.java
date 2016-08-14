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
// Generated from file `Page.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.joken.common.rpc;

/**
 * 分页实体类
 **/
public class Page implements java.lang.Cloneable, java.io.Serializable
{
    /**
     * 当前页码
     *
     **/
    public int pageNum;

    /**
     * 当前页码
     *
     **/
    public int
    getPageNum()
    {
        return pageNum;
    }

    /**
     * 当前页码
     *
     **/
    public void
    setPageNum(int _pageNum)
    {
        pageNum = _pageNum;
    }

    /**
     * 每页记录数
     *
     **/
    public int pageSize;

    /**
     * 每页记录数
     *
     **/
    public int
    getPageSize()
    {
        return pageSize;
    }

    /**
     * 每页记录数
     *
     **/
    public void
    setPageSize(int _pageSize)
    {
        pageSize = _pageSize;
    }

    /**
     * 总页数
     *
     **/
    public int pageTotal;

    /**
     * 总页数
     *
     **/
    public int
    getPageTotal()
    {
        return pageTotal;
    }

    /**
     * 总页数
     *
     **/
    public void
    setPageTotal(int _pageTotal)
    {
        pageTotal = _pageTotal;
    }

    /**
     * 总记录数
     *
     **/
    public int totalNum;

    /**
     * 总记录数
     *
     **/
    public int
    getTotalNum()
    {
        return totalNum;
    }

    /**
     * 总记录数
     *
     **/
    public void
    setTotalNum(int _totalNum)
    {
        totalNum = _totalNum;
    }

    /**
     * 排序、大于、小于、between等参数
     *
     **/
    public java.util.Map<java.lang.String, java.lang.String> params;

    /**
     * 排序、大于、小于、between等参数
     *
     **/
    public java.util.Map<java.lang.String, java.lang.String>
    getParams()
    {
        return params;
    }

    /**
     * 排序、大于、小于、between等参数
     *
     **/
    public void
    setParams(java.util.Map<java.lang.String, java.lang.String> _params)
    {
        params = _params;
    }

    public Page()
    {
    }

    public Page(int pageNum, int pageSize, int pageTotal, int totalNum, java.util.Map<java.lang.String, java.lang.String> params)
    {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pageTotal = pageTotal;
        this.totalNum = totalNum;
        this.params = params;
    }

    public boolean
    equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        Page _r = null;
        if(rhs instanceof Page)
        {
            _r = (Page)rhs;
        }

        if(_r != null)
        {
            if(pageNum != _r.pageNum)
            {
                return false;
            }
            if(pageSize != _r.pageSize)
            {
                return false;
            }
            if(pageTotal != _r.pageTotal)
            {
                return false;
            }
            if(totalNum != _r.totalNum)
            {
                return false;
            }
            if(params != _r.params)
            {
                if(params == null || _r.params == null || !params.equals(_r.params))
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
        __h = IceInternal.HashUtil.hashAdd(__h, "::com::joken::common::rpc::Page");
        __h = IceInternal.HashUtil.hashAdd(__h, pageNum);
        __h = IceInternal.HashUtil.hashAdd(__h, pageSize);
        __h = IceInternal.HashUtil.hashAdd(__h, pageTotal);
        __h = IceInternal.HashUtil.hashAdd(__h, totalNum);
        __h = IceInternal.HashUtil.hashAdd(__h, params);
        return __h;
    }

    public Page
    clone()
    {
        Page c = null;
        try
        {
            c = (Page)super.clone();
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
        __os.writeInt(pageNum);
        __os.writeInt(pageSize);
        __os.writeInt(pageTotal);
        __os.writeInt(totalNum);
        ParamsMapHelper.write(__os, params);
    }

    public void
    __read(IceInternal.BasicStream __is)
    {
        pageNum = __is.readInt();
        pageSize = __is.readInt();
        pageTotal = __is.readInt();
        totalNum = __is.readInt();
        params = ParamsMapHelper.read(__is);
    }

    static public void
    __write(IceInternal.BasicStream __os, Page __v)
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

    static public Page
    __read(IceInternal.BasicStream __is, Page __v)
    {
        if(__v == null)
        {
             __v = new Page();
        }
        __v.__read(__is);
        return __v;
    }
    
    private static final Page __nullMarshalValue = new Page();

    public static final long serialVersionUID = -1633867218L;
}