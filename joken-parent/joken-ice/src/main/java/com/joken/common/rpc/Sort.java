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
// Generated from file `Sort.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.joken.common.rpc;

/**
 * 通用排序模型
 *
 **/
public class Sort implements java.lang.Cloneable, java.io.Serializable
{
    /**
     * 排序Map变量,key对应model属性,value为排序方式(desc or asc)
     *
     **/
    public java.util.Map<java.lang.String, java.lang.String> sortMap;

    /**
     * 排序Map变量,key对应model属性,value为排序方式(desc or asc)
     *
     **/
    public java.util.Map<java.lang.String, java.lang.String>
    getSortMap()
    {
        return sortMap;
    }

    /**
     * 排序Map变量,key对应model属性,value为排序方式(desc or asc)
     *
     **/
    public void
    setSortMap(java.util.Map<java.lang.String, java.lang.String> _sortMap)
    {
        sortMap = _sortMap;
    }

    public Sort()
    {
    }

    public Sort(java.util.Map<java.lang.String, java.lang.String> sortMap)
    {
        this.sortMap = sortMap;
    }

    public boolean
    equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        Sort _r = null;
        if(rhs instanceof Sort)
        {
            _r = (Sort)rhs;
        }

        if(_r != null)
        {
            if(sortMap != _r.sortMap)
            {
                if(sortMap == null || _r.sortMap == null || !sortMap.equals(_r.sortMap))
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
        __h = IceInternal.HashUtil.hashAdd(__h, "::com::joken::common::rpc::Sort");
        __h = IceInternal.HashUtil.hashAdd(__h, sortMap);
        return __h;
    }

    public Sort
    clone()
    {
        Sort c = null;
        try
        {
            c = (Sort)super.clone();
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
        SortMapHelper.write(__os, sortMap);
    }

    public void
    __read(IceInternal.BasicStream __is)
    {
        sortMap = SortMapHelper.read(__is);
    }

    static public void
    __write(IceInternal.BasicStream __os, Sort __v)
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

    static public Sort
    __read(IceInternal.BasicStream __is, Sort __v)
    {
        if(__v == null)
        {
             __v = new Sort();
        }
        __v.__read(__is);
        return __v;
    }
    
    private static final Sort __nullMarshalValue = new Sort();

    public static final long serialVersionUID = 1361859403L;
}
