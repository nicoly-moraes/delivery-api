package com.delivery.api.services;

import com.delivery.api.dtos.CreateRestaurantDto;
import com.delivery.api.entities.Restaurant;
import com.delivery.api.entities.User;
import com.delivery.api.entities.UserType;
import com.delivery.api.repositories.RestaurantRepository;
import com.delivery.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Restaurant create(CreateRestaurantDto data) {
        var user = new User();
        user.setEmail(data.email());
        user.setPassword(passwordEncoder.encode(data.password()));
        user.setType(UserType.RESTAURANT);
        user = userRepository.save(user);

        var restaurant = new Restaurant();
        restaurant.setUserId(user.getId());
        restaurant.setName(data.name());
        restaurant.setPostalCode(data.postalCode());
        restaurant.setAddressLine1(data.addressLine1());
        restaurant.setCity(data.city());
        restaurant.setState(data.state());
        restaurant.setCountry(data.country());
        restaurant.setDeliveryPrice(data.deliveryPrice());
        restaurant.setDeliveryRadius(data.deliveryRadius());
        restaurant.setLatitude(data.latitude());
        restaurant.setLongitude(data.longitude());
        restaurant.setPhoneNumber(data.phoneNumber());

        if (data.image() != null && !data.image().isBlank()) {
            try {
                // Remove o prefixo "data:image/png;base64," (se houver)
                String base64Data = data.image().split(",")[1];

                // Decodifica a string Base64 para um array de bytes
                byte[] imageBytes = Base64.getDecoder().decode(base64Data);

                // Gera um nome de arquivo para a imagem (por exemplo, um UUID)
                String fileName = System.currentTimeMillis() + ".png";

                // Cria o diretório se não existir
                Path uploadPath = Path.of("src/main/resources/uploads/");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Salva a imagem no diretório de uploads
                Path imagePath = uploadPath.resolve(fileName);
                Files.write(imagePath, imageBytes);

                restaurant.setImage(imagePath.toString());
            } catch (IOException ignored) {}
        }

        if (data.addressLine2() != null && !data.addressLine2().isBlank()) {
            restaurant.setAddressLine2(data.addressLine2());
        }

        return restaurantRepository.save(restaurant);
    }
}
