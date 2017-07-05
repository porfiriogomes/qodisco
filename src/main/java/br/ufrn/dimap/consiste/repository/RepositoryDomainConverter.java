package br.ufrn.dimap.consiste.repository;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufrn.dimap.consiste.domain.DomainEntity;
import br.ufrn.dimap.consiste.domain.DomainRepository;

@Component
public class RepositoryDomainConverter extends PropertyEditorSupport {

	@Autowired
	private DomainRepository domainRepository;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		DomainEntity domain = domainRepository.findOne(text);
		setValue(domain);
	}
	
}
