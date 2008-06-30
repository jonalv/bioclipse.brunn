package net.bioclipse.brunn.genericDAO;

import org.springframework.aop.support.DefaultIntroductionAdvisor;


public class FinderIntroductionAdvisor extends DefaultIntroductionAdvisor {
    public FinderIntroductionAdvisor() {
        super(new FinderIntroductionInterceptor());
    }
} 