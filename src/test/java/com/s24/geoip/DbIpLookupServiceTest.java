package com.s24.geoip;

import com.google.common.net.InetAddresses;
import com.s24.geoip.impl.DbIpLookupService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DbIpLookupServiceTest {

    private DbIpLookupService service;

    @Test
    public void testLookupIpv4() throws Exception {
        GeoIpEntry entry1 = new GeoIpEntry(InetAddresses.forString("192.168.0.0"), InetAddresses.forString("192.168.0.255"), "ZZ", null, null, null, null, null, null, null, null, null, null);
        GeoIpEntry entry2 = new GeoIpEntry(InetAddresses.forString("192.168.1.0"), InetAddresses.forString("192.168.2.255"), "ZZ", null, null, null, null, null, null, null, null, null, null);
        service = new DbIpLookupService(entry1, entry2);

        assertEquals(entry1, service.lookup(InetAddresses.forString("192.168.0.0")));
        assertEquals(entry1, service.lookup(InetAddresses.forString("192.168.0.1")));
        assertEquals(entry1, service.lookup(InetAddresses.forString("192.168.0.254")));
        assertEquals(entry1, service.lookup(InetAddresses.forString("192.168.0.255")));
        assertEquals(entry2, service.lookup(InetAddresses.forString("192.168.1.0")));
        assertEquals(entry2, service.lookup(InetAddresses.forString("192.168.1.1")));
        assertEquals(entry2, service.lookup(InetAddresses.forString("192.168.2.254")));
        assertEquals(entry2, service.lookup(InetAddresses.forString("192.168.2.255")));
    }

    @Test
    public void testLookupIpv6() throws Exception {
        GeoIpEntry entry1 = new GeoIpEntry(InetAddresses.forString("2001:db8:1::"), InetAddresses.forString("2001:db8:1:ffff:ffff:ffff:ffff:ffff"), "ZZ", null, null, null, null, null, null, null, null, null, null);
        GeoIpEntry entry2 = new GeoIpEntry(InetAddresses.forString("2001:db8:2::"), InetAddresses.forString("2001:db8:2:ffff:ffff:ffff:ffff:ffff"), "ZZ", null, null, null, null, null, null, null, null, null, null);
        service = new DbIpLookupService(entry1, entry2);

        assertEquals(entry1, service.lookup(InetAddresses.forString("2001:db8:1:0:0:0:0:1")));
        assertEquals(entry1, service.lookup(InetAddresses.forString("2001:db8:1:ffff:ffff:ffff:ffff:fffe")));
        assertEquals(entry2, service.lookup(InetAddresses.forString("2001:db8:2:0:0:0:0:1")));
        assertEquals(entry2, service.lookup(InetAddresses.forString("2001:db8:2:ffff:ffff:ffff:ffff:fffe")));
    }

    @Test
    public void testIpv4AndIpv6Mixed() throws Exception {
        GeoIpEntry ipv4Entry = new GeoIpEntry(InetAddresses.forString("251.155.0.0"), InetAddresses.forString("251.155.255.255"), "ZZ", null, null, null, null, null, null, null, null, null, null);
        GeoIpEntry ipv6Entry = new GeoIpEntry(InetAddresses.forString("2001:db8:1::"), InetAddresses.forString("2001:db8:1:ffff:ffff:ffff:ffff:ffff"), "ZZ", null, null, null, null, null, null, null, null, null, null);
        service = new DbIpLookupService(ipv4Entry, ipv6Entry);

        assertEquals(ipv4Entry, service.lookup(InetAddresses.forString("251.155.0.1")));
        assertEquals(ipv4Entry, service.lookup(InetAddresses.forString("251.155.255.1")));
        assertEquals(ipv6Entry, service.lookup(InetAddresses.forString("2001:db8:1:0:0:0:0:1")));
        assertEquals(ipv6Entry, service.lookup(InetAddresses.forString("2001:db8:1:ffff:ffff:ffff:ffff:fffe")));
    }
}
