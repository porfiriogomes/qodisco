package br.ufrn.dimap.consiste.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.ufrn.dimap.consiste.authority.AuthorityEntity;
import br.ufrn.dimap.consiste.utils.BaseEntity;

@Entity
@Table(name="tb_user")
public class UserEntity extends BaseEntity implements UserDetails{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	@Column(unique=true)
	private String username;
	
	@NotBlank
	@Size(min=6, max=255, message="The password must have at least 6 characters.")
	private String password;
	
	@Email
	private String email;
	
	@NotBlank
	private String name;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<AuthorityEntity> permissions;
	
	public UserEntity() {}
	
	public UserEntity(Integer id, String username, String password, String email, String name,
			Set<AuthorityEntity> permissions) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.permissions = permissions;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<AuthorityEntity> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<AuthorityEntity> permissions) {
		this.permissions = permissions;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void addPermission(AuthorityEntity permission) {
		if (permissions==null)
			permissions = new HashSet<AuthorityEntity>();
		permissions.add(permission);
	}
	
	// User Details methods	to allow the integration with spring boot
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(AuthorityEntity authority : getPermissions())
			authorities.add(new SimpleGrantedAuthority(authority.getName()));
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
