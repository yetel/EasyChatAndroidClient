package com.king.easychat

import com.king.easychat.netty.packet.Packet
import com.king.easychat.netty.packet.req.GroupMessageReq
import org.junit.Test

import org.junit.Assert.*
import java.nio.file.Files.isDirectory
import io.netty.util.internal.ResourcesUtil.getFile
import java.io.File
import java.io.IOException


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testPacket() {
        var req = GroupMessageReq("123", "456", 1)
        println(req.messageType)
        println(req.packetType())
    }
    @Test
    fun testClass() {
        val clazz = Packet::class.java
        val classes = getClasses(clazz)
        println(classes)
    }

    /**
     * 取得当前类路径下的所有类
     *
     * @param cls
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Throws(IOException::class, ClassNotFoundException::class)
    fun getClasses(cls: Class<*>): List<Class<*>> {
        val pk = cls.getPackage()!!.name
        val path = pk.replace('.', '/')
        val classloader = Thread.currentThread().contextClassLoader
        val url = classloader!!.getResource(path)
        return getClasses(File(url.file), pk)
    }

    /**
     * 迭代查找类
     *
     * @param dir
     * @param pk
     * @return
     * @throws ClassNotFoundException
     */
    @Throws(ClassNotFoundException::class)
    private fun getClasses(dir: File, pk: String): List<Class<*>> {
        val classes = ArrayList<Class<*>>()
        if (!dir.exists()) {
            return classes
        }
        for (f in dir.listFiles()) {
            if (f.isDirectory()) {
                classes.addAll(getClasses(f, pk + "." + f.getName()))
            }
            val name = f.getName()
            if (name.endsWith(".class")) {
                classes.add(Class.forName(pk + "." + name.substring(0, name.length - 6)))
            }
        }
        return classes
    }
}
