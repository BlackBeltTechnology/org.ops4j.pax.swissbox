package org.ops4j.pax.demo.standalone;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;
import org.osgi.framework.Constants;
import static org.ops4j.pax.tinybundles.core.TinyBundles.*;
import org.ops4j.pax.tinybundles.demo.HelloWorld;
import org.ops4j.pax.tinybundles.demo.intern.HelloWorldImpl;
import org.ops4j.pax.tinybundles.demo.intern.MyFirstActivator;

/**
 * @author Toni Menzel (tonit)
 * @since Apr 20, 2009
 */
public class BndTest
{

    @Test
    public void tryBnd()
        throws IOException
    {
        InputStream inp = newBundle()
            .addClass( MyFirstActivator.class )
            .addClass( HelloWorld.class )
            .addClass( HelloWorldImpl.class )
            .prepare(
                withBnd()
                    .set( Constants.BUNDLE_SYMBOLICNAME, "MyFirstTinyBundle" )
                    .set( Constants.EXPORT_PACKAGE, "org.ops4j.pax.tinybundles.demo" )
                    .set( Constants.BUNDLE_ACTIVATOR, MyFirstActivator.class.getName() )
            ).build( asStream() );

        // test output
        JarInputStream jout = new JarInputStream( inp );
        Manifest man = jout.getManifest();
        assertEquals( "org.ops4j.pax.tinybundles.demo;resolution:=optional,org.osgi.framework;resolution:=optional",
                      man.getMainAttributes().getValue( Constants.IMPORT_PACKAGE )
        );
        assertEquals( "org.ops4j.pax.tinybundles.demo", man.getMainAttributes().getValue( Constants.EXPORT_PACKAGE ) );

        jout.close();
    }


}
