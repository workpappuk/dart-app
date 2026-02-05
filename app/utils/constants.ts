import { MOCK_AUDITABLE_USER } from "./mock";
import { Post } from "./types";

export const SESSION_TOKEN_KEY = 'session_token';
export const AuthorizationKey = "Authorization";

export const FeatureFlags = {
    ENABLE_OFFLINE_MODE: false,
    ENABLE_COMMUNITY_CREATION: true,
    ENABLE_POST_CREATION: true,
    ENABLE_VOTE_SYSTEM: true,
    ENABLE_COMMENTS: true,
};

export const API_BASE_URL = 'https://jubilant-spork-4jvq9vw99gpr3q746-8080.app.github.dev';
// export const API_BASE_URL = 'http://localhost:8080';


export const INTERESTING_AREAS = [
    { emoji: "🍣", label: "Anime & Cosplay" },
    { emoji: "🧑‍🎨", label: "Art" },
    { emoji: "💵", label: "Business & Finance" },
    { emoji: "🧩", label: "Collectibles & Other Hobbies" },
    { emoji: "🧑‍🏫", label: "Education & Career" },
    { emoji: "🪞", label: "Fashion & Beauty" },
    { emoji: "🍔", label: "Food & Drinks" },
    { emoji: "🕹️", label: "Games" },
    { emoji: "❤️‍🩹", label: "Health" },
    { emoji: "🏡", label: "Home & Garden" },
    { emoji: "📜", label: "Humanities & Law" },
    { emoji: "🌈", label: "Identity & Relationships" },
    { emoji: "🙉", label: "Internet Culture" },
    { emoji: "🎞️", label: "Movies & TV" },
    { emoji: "🎶", label: "Music" },
    { emoji: "🌿", label: "Nature & Outdoors" },
    { emoji: "📰", label: "News & Politics" },
    { emoji: "🌐", label: "Places & Travel" },
    { emoji: "✨", label: "Pop Culture" },
    { emoji: "✏️", label: "Q&As & Stories" },
    { emoji: "📖", label: "Reading & Writing" },
    { emoji: "🧪", label: "Sciences" },
    { emoji: "💀", label: "Spooky" },
    { emoji: "🏅", label: "Sports" },
    { emoji: "🛰️", label: "Technology" },
    { emoji: "🚗", label: "Vehicles" },
    { emoji: "🧘", label: "Wellness" },
    { emoji: "🟥", label: "Adult Content" },
    { emoji: "🔞", label: "Mature Topics" }
];

export const MODERATION_TOPICS = [
    "Spam",
    "Harassment",
    "Hate Speech",
    "Misinformation",
    "Violence",
    "Self-Harm",
    "Nudity or Sexual Content",
    "Illegal Activities",
    "Other"
];

export const DUMMY_COMMUNITIES = [
    {
        ...MOCK_AUDITABLE_USER, name: 'TechTalk', description: 'A community for tech enthusiasts to discuss the latest in technology.', membersCount: 1200
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'FoodiesUnite', description: 'Share recipes, restaurant reviews, and food photography.', membersCount: 850
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'TravelBuddies', description: 'Find travel companions and share travel tips and experiences.', membersCount: 640
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'BookLovers', description: 'A haven for book enthusiasts to discuss their favorite reads.', membersCount: 430
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'FitnessFreaks', description: 'Discuss workout routines, nutrition, and fitness goals.', membersCount: 980
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'MovieManiacs', description: 'Talk about the latest movies, reviews, and film trivia.', membersCount: 720
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'GamingGuild', description: 'A community for gamers to discuss games, strategies, and news.', membersCount: 1500
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'ArtisticExpressions', description: 'Share your artwork, get feedback, and discuss art techniques.', membersCount: 560
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'MusicMakers', description: 'Discuss music production, share tracks, and collaborate with other musicians.', membersCount: 400
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'NatureNook', description: 'A community for nature lovers to share photos, tips, and experiences.', membersCount: 300
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'ScienceSquad', description: 'Discuss the latest scientific discoveries and research.', membersCount: 750
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'HistoryHub', description: 'A place to discuss historical events, figures, and theories.', membersCount: 520
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'PhotographyPros', description: 'Share your photography, get tips, and discuss techniques.', membersCount: 680
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'DIYDreamers', description: 'A community for DIY enthusiasts to share projects and ideas.', membersCount: 410
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'ParentingPlace', description: 'Discuss parenting tips, challenges, and experiences.', membersCount: 900
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'FashionForward', description: 'Talk about the latest fashion trends and styles.', membersCount: 770
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'AutoAddicts', description: 'A community for car enthusiasts to discuss vehicles and modifications.', membersCount: 620
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'HealthHaven', description: 'Discuss health topics, wellness tips, and medical news.', membersCount: 580
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'SportsSpot', description: 'Talk about your favorite sports, teams, and athletes.', membersCount: 1100
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'ComedyCorner', description: 'Share jokes, memes, and funny stories to brighten your day.', membersCount: 450
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'GardeningGurus', description: 'A community for gardening enthusiasts to share tips and photos.', membersCount: 350
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'TechInnovators', description: 'Discuss the latest innovations and trends in technology.', membersCount: 1300
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'Cinephiles', description: 'A place for movie lovers to discuss films and directors.', membersCount: 600
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'TravelTales', description: 'Share your travel stories and tips with fellow travelers.', membersCount: 480
    },
    {
        ...MOCK_AUDITABLE_USER, name: 'FoodFanatics', description: 'A community for food lovers to share recipes and restaurant reviews.', membersCount: 720
    }
];

export const DUMMY_POSTS: Post[] = [
    {

        ...MOCK_AUDITABLE_USER, title: 'Best Programming Languages in 2024', content: 'What are the best programming languages to learn in 2024?',
        community: 'TechTalk', votes: 120, media: [
            { type: 'image', url: 'https://example.com/image1.jpg', caption: 'Programming Languages 2024' },
            { type: 'video', url: 'https://example.com/video1.mp4', caption: 'Programming Languages Overview' },
            { type: 'text', text: 'In 2024, the top programming languages are expected to be Python, JavaScript, Rust, and Go.' },
            { type: 'poll', question: 'Which programming language do you prefer?', options: ['Python', 'JavaScript', 'Rust', 'Go'] },
            { type: 'quote', text: 'The best way to predict the future is to invent it. - Alan Kay' },
            { type: 'code', code: 'console.log("Hello, World!");', language: 'javascript' },
            { type: 'file', url: 'https://example.com/file1.pdf', caption: 'Programming Languages Report' },
            { type: 'audio', url: 'https://example.com/audio1.mp3', caption: 'Programming Languages Podcast' },
            { type: 'event', title: 'Programming Languages Webinar', date: '2024-05-01T18:00:00Z', description: 'Join us for a webinar on the best programming languages in 2024.' },
            { type: 'location', latitude: 37.7749, longitude: -122.4194, caption: 'Programming Conference Location' },
            {
                type: 'product',
                name: 'Programming Languages Book', price: 29.99, url: 'https://example.com/product1', caption: 'Learn about programming languages with this comprehensive book.'
            },
            {
                type: 'profile', username: 'TechGuru', displayName: 'Tech Guru', avatarUrl: 'https://example.com/avatar1.jpg', caption: 'Author of the post'
            },
            { type: 'survey', question: 'Which programming language do you think will be the most popular in 2024?', options: ['Python', 'JavaScript', 'Rust', 'Go'] },
            { type: 'announcement', title: 'Programming Languages Update', content: 'We have updated our list of the best programming languages for 2024 based on new trends and data.' },
            { type: 'countdown', event: 'Programming Languages Webinar', endTime: '2024-05-01T18:00:00Z', caption: 'Countdown to our programming languages webinar' },
            {
                type: 'badge',
                name: 'Top Tech Post', description: 'Awarded for creating a top post in the TechTalk community', imageUrl: 'https://example.com/badge1.png'
            },
            {
                type: 'achievement',
                name: 'Tech Guru', description: 'Achieved 1000 upvotes on posts in the TechTalk community', date: '2024-04-15T12:00:00Z', imageUrl: 'https://example.com/achievement1.png'
            },
            {
                type: 'milestone',
                name: '1000 Upvotes', description: 'Reached 1000 upvotes on posts in the TechTalk community', date: '2024-04-15T12:00:00Z', imageUrl: 'https://example.com/milestone1.png'
            },
            { type: 'caseStudy', title: 'Programming Languages in Industry', content: 'A case study on the most popular programming languages used in the industry in 2024.' },
            { type: 'infographic', url: 'https://example.com/infographic1.jpg', caption: 'Programming Languages Popularity in 2024' },
            { type: 'map', latitude: 37.7749, longitude: -122.4194, caption: 'Tech Hubs Around the World' },
            {
                type: 'timeline', events: [
                    { date: '2024-01-01', event: 'Python becomes the most popular programming language' },
                    { date: '2024-02-15', event: 'JavaScript sees a surge in usage for web development' },
                    { date: '2024-03-10', event: 'Rust gains popularity for systems programming' },
                    { date: '2024-04-05', event: 'Go is widely adopted for cloud-native applications' }
                ], caption: 'Programming Languages Trends in 2024'
            },
            {
                type: 'chart', chartType: 'bar', data: {
                    labels: ['Python', 'JavaScript', 'Rust', 'Go'],
                    datasets: [
                        {
                            label: 'Popularity',
                            data: [40, 30, 20, 10]
                        }
                    ]
                }, caption: 'Programming Languages Popularity Chart'
            },
            {
                type: 'table', headers: ['Language', 'Use Case', 'Popularity'], rows: [
                    ['Python', 'Data Science, Web Development', '40%'],
                    ['JavaScript', 'Web Development', '30%'],
                    ['Rust', 'Systems Programming', '20%'],
                    ['Go', 'Cloud-Native Applications', '10%']
                ], caption: 'Programming Languages Comparison Table'
            },
            {
                type: 'slideshow', slides: [
                    { url: 'https://example.com/slide1.jpg', caption: 'Python Overview' },
                    { url: 'https://example.com/slide2.jpg', caption: 'JavaScript Overview' },
                    { url: 'https://example.com/slide3.jpg', caption: 'Rust Overview' },
                    { url: 'https://example.com/slide4.jpg', caption: 'Go Overview' }
                ], caption: 'Programming Languages Slideshow'
            },

            { type: 'animation', url: 'https://example.com/animation1.gif', caption: 'Programming Languages Animation' },
            { type: 'virtualTour', url: 'https://example.com/virtualtour1', caption: 'Virtual Tour of Programming Languages History' },
            { type: '3dModel', url: 'https://example.com/3dmodel1', caption: '3D Model of Programming Languages Ecosystem' },
            { type: 'webinar', title: 'Learn the Best Programming Languages in 2024', date: '2024-06-01T18:00:00Z', description: 'Join our webinar to learn about the best programming languages to learn in 2024.' },
            { type: 'workshop', title: 'Hands-on Workshop on Programming Languages', date: '2024-06-15T10:00:00Z', description: 'Participate in our workshop to get hands-on experience with the top programming languages of 2024.' },
            { type: 'ebook', title: 'The Ultimate Guide to Programming Languages in 2024', url: 'https://example.com/ebook1.pdf', description: 'Download our comprehensive guide to the best programming languages in 2024.' },
            { type: 'newsletter', title: 'Programming Languages Newsletter', url: 'https://example.com/newsletter1', description: 'Subscribe to our newsletter for the latest updates on programming languages in 2024.' },
            { type: 'caseStudy', title: 'Adoption of Programming Languages in Tech Industry', content: 'An in-depth case study on how different programming languages are being adopted in the tech industry in 2024.' },
            { type: 'researchPaper', title: 'Trends in Programming Languages for 2024', url: 'https://example.com/researchpaper1.pdf', abstract: 'This research paper explores the emerging trends in programming languages for the year 2024.' },
            {
                type: 'faq', faqs: [
                    { question: 'What are the best programming languages to learn in 2024?', answer: 'The best programming languages to learn in 2024 are Python, JavaScript, Rust, and Go.' },
                    { question: 'Why is Python so popular?', answer: 'Python is popular due to its simplicity, versatility, and strong community support.' },
                    { question: 'What is Rust used for?', answer: 'Rust is primarily used for systems programming and is known for its performance and safety features.' }
                ], caption: 'Frequently Asked Questions about Programming Languages in 2024'
            },
            {
                type: 'gallery',
                images: [
                    { url: 'https://example.com/gallery1.jpg', caption: 'Python' },
                    { url: 'https://example.com/gallery2.jpg', caption: 'JavaScript' },
                    { url: 'https://example.com/gallery3.jpg', caption: 'Rust' },
                    { url: 'https://example.com/gallery4.jpg', caption: 'Go' }
                ],
                caption: 'Gallery of Programming Languages'
            },
            { type: 'link', url: 'https://www.example.com/best-programming-languages-2024', caption: 'Read more about the best programming languages in 2024' },
            { type: 'summary', text: 'In summary, 2024 is shaping up to be an exciting year for programming languages with Python, JavaScript, Rust, and Go leading the way.' },
            { type: 'reflection', prompt: 'How will the choice of programming language impact your projects in 2024?', response: 'Choosing the right programming language can significantly affect the efficiency, scalability, and maintainability of my projects in 2024.' },
            { type: 'challenge', title: '30-Day Coding Challenge', description: 'Participate in our 30-day coding challenge to improve your skills in Python, JavaScript, Rust, or Go.', rules: 'Code every day for 30 days and share your progress with the community.' },
            { type: 'collaboration', projectTitle: 'Open Source Project on Programming Languages', description: 'Join our open source project to contribute to tools and libraries for the top programming languages in 2024.', roles: 'We are looking for developers, testers, and documentation writers to join our team.' },
            { type: 'sponsorship', sponsorName: 'TechCorp', description: 'This post is sponsored by TechCorp, a leading provider of tech solutions.', url: 'https://www.techcorp.com' },
        ]
    },
    {

        ...MOCK_AUDITABLE_USER, title: 'Top Travel Destinations for 2024', content: 'Share your favorite travel destinations and tips for 2024.',
        community: 'TravelBuddies', votes: 85,
        media: [
            { type: 'image', url: 'https://example.com/travel1.jpg', caption: 'Travel Destinations 2024' },
            { type: 'video', url: 'https://example.com/travelvideo1.mp4', caption: 'Top Travel Destinations Overview' },
            { type: 'text', text: 'The top travel destinations for 2024 include Bali, Japan, Italy, and New Zealand.' },
            { type: 'poll', question: 'Which travel destination are you most excited to visit in 2024?', options: ['Bali', 'Japan', 'Italy', 'New Zealand'] },
            { type: 'quote', text: 'Travel is the only thing you buy that makes you richer. - Anonymous' },
            { type: 'map', latitude: 0, longitude: 0, caption: 'Map of Top Travel Destinations' }
        ]
    },
    {

        ...MOCK_AUDITABLE_USER, title: 'Healthy Meal Prep Ideas', content: 'Share your favorite healthy meal prep ideas and recipes.',
        community: 'FoodiesUnite', votes: 60,
        media: [
            { type: 'image', url: 'https://example.com/mealprep1.jpg', caption: 'Healthy Meal Prep Ideas' },
            { type: 'video', url: 'https://example.com/mealprepvideo1.mp4', caption: 'Healthy Meal Prep Recipes' },
            { type: 'text', text: 'Some of my favorite healthy meal prep ideas include quinoa salad, grilled chicken with veggies, and overnight oats.' },
            { type: 'poll', question: 'What is your go-to healthy meal prep recipe?', options: ['Quinoa Salad', 'Grilled Chicken with Veggies', 'Overnight Oats', 'Other'] },
            { type: 'quote', text: 'Let food be thy medicine and medicine be thy food. - Hippocrates' }
        ]
    },
    {

        ...MOCK_AUDITABLE_USER, title: 'Best Workout Routines for Beginners', content: 'What are some effective workout routines for beginners?',
        community: 'FitnessFreaks', votes: 90,
        media: [
            { type: 'image', url: 'https://example.com/workout1.jpg', caption: 'Workout Routines for Beginners' },
            { type: 'video', url: 'https://example.com/workoutvideo1.mp4', caption: 'Beginner Workout Routine' },
            { type: 'text', text: 'A great workout routine for beginners includes a mix of cardio, strength training, and flexibility exercises.' },
            { type: 'poll', question: 'What is your favorite type of workout?', options: ['Cardio', 'Strength Training', 'Flexibility Exercises', 'Other'] },
            { type: 'quote', text: 'The only bad workout is the one that didn\'t happen. - Anonymous' },
            { type: 'challenge', title: '7-Day Fitness Challenge', description: 'Join our 7-day fitness challenge to kickstart your workout routine.', rules: 'Complete a workout every day for 7 days and share your progress with the community.' },
            { type: 'collaboration', projectTitle: 'Community Workout Plan', description: 'Contribute to our community workout plan by sharing your favorite exercises and routines.', roles: 'We are looking for fitness enthusiasts to help create a comprehensive workout plan for beginners.' }
        ]
    },
    {
        ...MOCK_AUDITABLE_USER, title: 'Must-Watch Movies of 2024', content: 'What are the must-watch movies coming out in 2024?',
        community: 'MovieManiacs', votes: 110, media: [{ type: 'image', url: 'https://example.com/movies1.jpg', caption: 'Must-Watch Movies 2024' }]
    },
    {
        ...MOCK_AUDITABLE_USER, title: 'Best Gaming Laptops in 2024', content: 'What are the best gaming laptops available in 2024?',
        community: 'GamingGuild', votes: 75, media: [{ type: 'image', url: 'https://example.com/image2.jpg', caption: 'Gaming Laptops 2024' }]
    },
    {
        ...MOCK_AUDITABLE_USER, title: 'Photography Tips for Beginners', content: 'Share your best photography tips for beginners.',
        community: 'PhotographyPros', votes: 50, media: [{ type: 'image', url: 'https://example.com/image3.jpg', caption: 'Photography Tips' }]
    },
    {
        ...MOCK_AUDITABLE_USER, title: 'DIY Home Decor Ideas', content: 'What are some creative DIY home decor ideas?',
        community: 'DIYDreamers', votes: 40, media: [{ type: 'image', url: 'https://example.com/image4.jpg', caption: 'DIY Home Decor' }]
    },
    {
        ...MOCK_AUDITABLE_USER, title: 'Best Parenting Books', content: 'What are the best books for parenting advice?',
        community: 'ParentingPlace', votes: 70, media: [{ type: 'image', url: 'https://example.com/parenting1.jpg', caption: 'Best Parenting Books' }]
    },
    {
        ...MOCK_AUDITABLE_USER, title: 'Latest Fashion Trends for 2024', content: 'What are the latest fashion trends to watch out for in 2024?',
        community: 'FashionForward', votes: 95, media: [{ type: 'image', url: 'https://example.com/fashion1.jpg', caption: 'Latest Fashion Trends 2024' }]
    },
    {
        ...MOCK_AUDITABLE_USER, title: 'Top 5 Electric Cars of 2024', content: 'What are the top electric cars available in 2024?',
        community: 'AutoAddicts', votes: 80, media: [{ type: 'image', url: 'https://example.com/cars1.jpg', caption: 'Top Electric Cars 2024' }]
    },
    {
        ...MOCK_AUDITABLE_USER, title: 'Mental Health Awareness Tips', content: 'Share your tips for raising mental health awareness and supporting those in need.',
        community: 'HealthHaven', votes: 65, media: [{ type: 'image', url: 'https://example.com/health1.jpg', caption: 'Mental Health Awareness' }]
    },
    {
        ...MOCK_AUDITABLE_USER, title: 'Best Sports Moments of 2024', content: 'What are the best sports moments that happened in 2024?',
        community: 'SportsSpot', votes: 100, media: [{ type: 'video', url: 'https://example.com/sports1.mp4', caption: 'Best Sports Moments 2024' }]
    },
    {
        ...MOCK_AUDITABLE_USER, title: 'Funniest Memes of 2024', content: 'Share the funniest memes that you came across in 2024.',
        community: 'ComedyCorner', votes: 55, media: [{ type: 'image', url: 'https://example.com/memes1.jpg', caption: 'Funniest Memes 2024' }]
    },
    {
        ...MOCK_AUDITABLE_USER, title: 'Gardening Tips for Spring', content: 'What are your best gardening tips for the spring season?',
        community: 'GardeningGurus', votes: 45, media: [{ type: 'image', url: 'https://example.com/garden1.jpg', caption: 'Gardening Tips for Spring' }]
    },
    {
        ...MOCK_AUDITABLE_USER, title: 'Tech Innovations to Watch in 2024', content: 'What are the most exciting tech innovations to watch out for in 2024?',
        community: 'TechInnovators', votes: 115, media: [{ type: 'video', url: 'https://example.com/tech1.mp4', caption: 'Tech Innovations 2024' }]
    },
    {
        ...MOCK_AUDITABLE_USER, title: 'Best Classic Movies to Rewatch', content: 'What are some classic movies that are worth rewatching?',
        community: 'Cinephiles', votes: 85, media: [{ type: 'image', url: 'https://example.com/classicmovies1.jpg', caption: 'Best Classic Movies' }]
    },
    {
        ...MOCK_AUDITABLE_USER, title: 'Travel Safety Tips for Solo Travelers', content: 'What are your best travel safety tips for solo travelers?',
        community: 'TravelTales', votes: 60, media: [{ type: 'image', url: 'https://example.com/travelsafety1.jpg', caption: 'Travel Safety Tips' }]
    },
    {
        ...MOCK_AUDITABLE_USER, title: 'Best Street Food Around the World', content: 'What are some of the best street food dishes you have tried around the world?',
        community: 'FoodFanatics', votes: 70, media: [{ type: 'image', url: 'https://example.com/streetfood1.jpg', caption: 'Best Street Food' }]
    },
    {
        ...MOCK_AUDITABLE_USER, title: 'Community Guidelines Reminder', content: 'Please remember to follow our community guidelines to keep our space safe and welcoming for everyone.',
        community: 'TechTalk', votes: 0, media: []
    }
];
