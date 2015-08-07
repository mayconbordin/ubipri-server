package services.credentials;

public class CredentialsProviderFactory {
	public static CredentialsProvider build(String type) {
		CredentialsProvider provider = null;
		
		switch (type) {
			case DefaultCredentialsProvider.NAME:
				provider = new DefaultCredentialsProvider();
				break;
				
			case SigaiCredentialsProvider.NAME:
				provider = new SigaiCredentialsProvider();
				break;
		}
		
		return provider;
	}
}
