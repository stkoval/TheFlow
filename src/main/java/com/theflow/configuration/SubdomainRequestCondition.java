package com.theflow.configuration;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

/**
 *
 * @author Stas
 */
public class SubdomainRequestCondition implements
        RequestCondition<SubdomainRequestCondition> {

    private final Set<String> subdomains;
    private final String tld;

    public SubdomainRequestCondition(String tld, String... subdomains) {
        this(tld, Arrays.asList(subdomains));
    }

    public SubdomainRequestCondition(String tld, Collection<String> subdomains) {
        this.subdomains = Collections.unmodifiableSet(new HashSet<String>(
                subdomains));
        this.tld = tld;
    }

    @Override
    public SubdomainRequestCondition combine(SubdomainRequestCondition other) {
        Set<String> allRoles = new LinkedHashSet<String>(this.subdomains);
        allRoles.addAll(other.subdomains);
        return new SubdomainRequestCondition(tld, allRoles);
    }

    @Override
    public SubdomainRequestCondition getMatchingCondition(
            HttpServletRequest request) {
        try {
            URL uri = new URL(request.getRequestURL().toString());
            String[] parts = uri.getHost().split(this.tld);
            if (parts.length == 1) {
                for (String s : this.subdomains) {
                    if (s.equalsIgnoreCase(parts[0])) {
                        return this;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return null;
    }

    @Override
    public int compareTo(SubdomainRequestCondition other,
            HttpServletRequest request) {
        return org.apache.commons.collections.CollectionUtils.removeAll(other.subdomains, this.subdomains).size();
    }

}
